package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import erp.workorder.dao.BoqItemDao;
import erp.workorder.dao.ChainageDao;
import erp.workorder.dao.HighwayBoqMapDao;
import erp.workorder.dao.StructureDao;
import erp.workorder.dao.WorkorderBasicDetailDao;
import erp.workorder.dao.WorkorderBoqWorkDao;
import erp.workorder.dao.WorkorderDao;
import erp.workorder.dto.ChainageBoqParentChildDTO;
import erp.workorder.dto.ChainageBoqQuantityMappingDTO;
import erp.workorder.dto.ChainageBoqsRenderDTO;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.MinimalObjectDTO;
import erp.workorder.dto.MinimalWorkorderBoqLocationDTO;
import erp.workorder.dto.SbqParentChildDTO;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.WorkorderBoqWorkDTO;
import erp.workorder.dto.WorkorderBoqWorkLocationDTO;
import erp.workorder.dto.WorkorderBoqWorkParentChildDTO;
import erp.workorder.dto.WorkorderBoqWorkQtyMappingDTO;
import erp.workorder.dto.WorkorderBoqWorkRenderDTO;
import erp.workorder.dto.request.AmendWorkorderInvocationUpdateStateRequest;
import erp.workorder.dto.response.WorkorderStructureTypeBoqResponse;
import erp.workorder.entity.BoqItem;
import erp.workorder.entity.CategoryItem;
import erp.workorder.entity.Chainage;
import erp.workorder.entity.ChainageBoqQuantityMapping;
import erp.workorder.entity.HighwayBoqMapping;
import erp.workorder.entity.Structure;
import erp.workorder.entity.StructureBoqQuantityMapping;
import erp.workorder.entity.StructureType;
import erp.workorder.entity.SubcategoryItem;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderBoqWork;
import erp.workorder.entity.WorkorderBoqWorkLocation;
import erp.workorder.entity.WorkorderBoqWorkLocationVersion;
import erp.workorder.entity.WorkorderBoqWorkQtyMapping;
import erp.workorder.entity.WorkorderBoqWorkQtyMappingVersion;
import erp.workorder.entity.WorkorderBoqWorkVersion;
import erp.workorder.entity.WorkorderCategoryMapping;
import erp.workorder.entity.WorkorderType;
import erp.workorder.entity.WorkorderVersion;
import erp.workorder.enums.ItemType;
import erp.workorder.enums.Responses;
import erp.workorder.enums.UnitTypes;
import erp.workorder.enums.WorkorderTypes;
import erp.workorder.service.WorkorderBoqWorkService;
import erp.workorder.util.SetObject;

@Transactional
@Service
public class WorkorderBoqWorkServiceImpl implements WorkorderBoqWorkService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private WorkorderBoqWorkDao woBoqWorkDao;
	@Autowired
	private WorkorderDao workorderDao;
	@Autowired
	private WorkorderBasicDetailDao woBasicDao;
	@Autowired
	private BoqItemDao boqDao;
	@Autowired
	private HighwayBoqMapDao hbmDao;
	@Autowired
	private ChainageDao chainageDao;
	@Autowired
	private StructureDao structureDao;

	@Transactional(readOnly = true)
	@Override
	public CustomResponse renderWoTypeWiseBoqWork(SearchDTO search) {

		try {
			if (search.getWorkorderId() != null) {
				Workorder workorder = workorderDao.fetchWorkorderById(search.getWorkorderId());
				if (workorder == null)
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "No workorder found.");

				List<WorkorderCategoryMapping> workorderCategories = woBasicDao
						.fetchWorkorderCategoriesByWorkorderId(search.getWorkorderId());
				Set<Long> workorderCategoryIds = new HashSet<>();
				if (workorderCategories != null) {
					for (WorkorderCategoryMapping wcm : workorderCategories) {
						workorderCategoryIds.add(wcm.getCategoryId());
					}
				}
				WorkorderType woType = workorder.getType();
				if (woType != null) {
					if (woType.getId().intValue() == WorkorderTypes.Highway.getId()) {
						if (search.getLocations() == null || search.getLocations().size() == 0) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Provide set of both chainage id's.");
						}
						for (WorkorderBoqWorkLocationDTO location : search.getLocations()) {
							if (location.getFromChainageId() == null || location.getToChainageId() == null) {
								return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
										"Provide chainage range ids in locations.");
							}
						}
						Chainage fromChainage = chainageDao.fetchById(search.getLocations().get(0).getFromChainageId());
						Chainage toChainage = chainageDao.fetchById(search.getLocations().get(0).getToChainageId());
						List<ChainageBoqQuantityMapping> cbqListAcc = woBoqWorkDao
								.fetchActiveCbqsBetweenChainageNumericValuesAndSiteIdAndWoCategories(
										fromChainage.getNumericChainageValue(), toChainage.getNumericChainageValue(),
										workorderCategoryIds, search.getSiteId());
						Set<Long> uniqueBoqIds = new HashSet<>();
						for (ChainageBoqQuantityMapping cbq : cbqListAcc) {
							uniqueBoqIds.add(cbq.getBoq().getId());
						}

						Map<Long, Double> boqChainageQuantityMapping = new HashMap<Long, Double>();
						List<ChainageBoqQuantityMapping> cbqList = new ArrayList<>();
						if (cbqList != null) {
							for (Long boqId : uniqueBoqIds) {
								for (ChainageBoqQuantityMapping cbq : cbqListAcc) {
									if (boqId.equals(cbq.getBoq().getId())) {
										cbqList.add(cbq);
									}
								}
								int counter = 0;
								Double area = 0.0;
								Double volume = 0.0;
								Boolean isVolumeBoq = false;
								for (ChainageBoqQuantityMapping cbq : cbqList) {
									if (cbq.getBoq().getUnit().getType().getId().equals(UnitTypes.VOLUME.getTypeId())) {
										isVolumeBoq = true;
									}
									ChainageBoqQuantityMappingDTO obj = setObject.chainageBoqQtyMapEntityToDto(cbq);
									obj.setArea((obj.getLhsQuantity() != null ? obj.getLhsQuantity() : 0.0)
											+ (obj.getRhsQuantity() != null ? obj.getRhsQuantity() : 0.0));
									if (obj.getChainage() != null && obj.getChainage().getPrevious() != null) {
										Integer length = ((Integer
												.parseInt(obj.getChainage().getName().replaceAll("[^0-9]", "")))
												- (Integer.parseInt(obj.getChainage().getPrevious().getName()
														.replaceAll("[^0-9]", ""))));
										obj.setLength(length.doubleValue());
										if (counter == 0) {
											obj.setLength(0.0);
											obj.setVolume(0.0);
											area += obj.getArea();
										}
										if (counter > 0) {
											Double previousArea = (cbqList.get(counter - 1).getLhsQuantity() != null
													? cbqList.get(counter - 1).getLhsQuantity()
													: 0.0)
													+ (cbqList.get(counter - 1).getRhsQuantity() != null
															? cbqList.get(counter - 1).getRhsQuantity()
															: 0.0);
											Double currentArea = obj.getArea();
											area += currentArea;
											double meanArea = (previousArea + currentArea) / 2;
											if (cbq.getStructureRemark() == null
													|| cbq.getStructureRemark().isBlank()) {
												obj.setVolume(meanArea * obj.getLength());
												volume += meanArea * obj.getLength();
											} else {
												obj.setVolume(0.0);
											}
										}
									} else {
										area += obj.getArea();
										obj.setLength(0.0);
										obj.setVolume(0.0);
									}
									counter++;
								}
								if (isVolumeBoq) {
									boqChainageQuantityMapping.put(boqId, volume);
								} else {
									boqChainageQuantityMapping.put(boqId, area);
								}
							}
						}

						search.setIdsArrSet(workorderCategoryIds);
						List<HighwayBoqMapping> hbqs = hbmDao.fetchHbqsByCategories(search);
						if (hbqs != null) {
							for (HighwayBoqMapping hbq : hbqs) {
								if (boqChainageQuantityMapping.get(hbq.getBoq().getId()) != null)
									hbq.setQuantity(boqChainageQuantityMapping.get(hbq.getBoq().getId()));
							}
						}
						List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWorkDao
								.fetchSiteChainagesWoBoqWorkQtys(search);
						if (woBoqQtys != null && hbqs != null) {
							for (HighwayBoqMapping hbq : hbqs) {
								Double workorderQuantity = 0.0;
								for (WorkorderBoqWorkQtyMapping obj : woBoqQtys) {
									if (obj.getBoq().getId().equals(hbq.getBoq().getId())) {
										workorderQuantity += obj.getQuantity();
									}
								}
								hbq.setQuantity(hbq.getQuantity().doubleValue() - workorderQuantity.doubleValue());
							}
						}

						List<BoqItem> boqs = boqDao.fetchBoqItems(search);
						ChainageBoqsRenderDTO result = setObject.renderChildParentHighwayBoqsInCbqFormat(hbqs, boqs);
						return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
					} else if (woType.getId().intValue() == WorkorderTypes.Structure.getId()) {
						Long structureTypeId = null;
						Long structureId = null;
						if (search.getLocations() == null || search.getLocations().size() == 0)
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Provide set of locations.");
						for (WorkorderBoqWorkLocationDTO location : search.getLocations()) {
							if (location.getStructureTypeId() == null) {
								return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
										"Provide structure type id in locations.");
							}
							structureTypeId = location.getStructureTypeId();
							structureId = location.getStructureId();
							break;
						}
						search.setStructureTypeId(structureTypeId);
						List<StructureBoqQuantityMapping> sbqs = woBoqWorkDao
								.fetchActiveSbqsByStructureTypeIdAndStructureIdAndCategoryIds(structureTypeId,
										structureId, workorderCategoryIds, search.getSiteId());
						search.setStructureId(structureId);
						List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWorkDao.fetchStructureWoBoqWorkQtys(search);
						List<StructureBoqQuantityMapping> sbqsAfterAccumulationOfQuantitiesAndWeightedRate = new ArrayList<>();

						if (woBoqQtys != null && sbqs != null) {

							HashSet<String> boqIdDescriptionUniquePairFlags = new HashSet<>();

							for (StructureBoqQuantityMapping sbq : sbqs) {
								Long boqId = sbq.getBoq().getId();
								String boqDescription = sbq.getDescription();
								String boqIdDescriptionUniquePairPattern = boqId + "/" + boqDescription;

								if (boqIdDescriptionUniquePairFlags.contains(boqIdDescriptionUniquePairPattern)) {
									continue;
								}
								boqIdDescriptionUniquePairFlags.add(boqIdDescriptionUniquePairPattern);
								Double rateWeighted = null;
								Double quantityAccumulated = 0.0;
								for (StructureBoqQuantityMapping sbqItr : sbqs) {

									if (boqId.equals(sbqItr.getBoq().getId())
											&& boqDescription.trim().equalsIgnoreCase(sbqItr.getDescription().trim())) {
										if (sbqItr.getBoq().getId() == 177L) {
											System.out.println();
										}
										if (rateWeighted == null) {
											rateWeighted = sbqItr.getRate();
										} else {
											rateWeighted = (rateWeighted + sbqItr.getRate()) / 2;
										}
										quantityAccumulated += sbqItr.getQuantity();
									}
								}
								sbq.setRate(rateWeighted == null ? 0.0 : rateWeighted);
								sbq.setQuantity(quantityAccumulated);
								sbqsAfterAccumulationOfQuantitiesAndWeightedRate.add(sbq);
							}

							for (StructureBoqQuantityMapping sbq : sbqsAfterAccumulationOfQuantitiesAndWeightedRate) {
								for (WorkorderBoqWorkQtyMapping obj : woBoqQtys) {
									if (obj.getBoq().getId().equals(sbq.getBoq().getId()) && obj.getDescription().trim()
											.equalsIgnoreCase(sbq.getDescription().trim())) {
										sbq.setQuantity(
												sbq.getQuantity().doubleValue() - obj.getQuantity().doubleValue());
										if (sbq.getQuantity().doubleValue() < 0.0) {
											sbq.setQuantity(0.0);
										}
									}
								}
							}
						}
						Object result = setObject
								.renderChildParentSbqs(sbqsAfterAccumulationOfQuantitiesAndWeightedRate).getData();
						List<WorkorderStructureTypeBoqResponse> resultResponse = new ArrayList<>();
						WorkorderStructureTypeBoqResponse structureBoq;
						if (structureId != null) {
							Structure structure = structureDao.fetchStructureById(structureId);
							structureBoq = new WorkorderStructureTypeBoqResponse(structure.getType().getId(),
									structure.getType().getName(), structure.getId(), structure.getName(), result);
						} else {
							StructureType structureType = woBoqWorkDao.fetchStructureTypeById(structureTypeId);
							structureBoq = new WorkorderStructureTypeBoqResponse(structureTypeId,
									structureType.getName(), null, null, result);
						}

						resultResponse.add(structureBoq);
						return new CustomResponse(Responses.SUCCESS.getCode(), resultResponse,
								Responses.SUCCESS.toString());
					}
				}
			}
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide workorder id");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse saveWorkorderBoqWork(WorkorderBoqWorkDTO boqWorkDTO) {

		try {
			SearchDTO search = new SearchDTO();
			if (boqWorkDTO.getWorkorderId() != null) {
				Workorder workorder = workorderDao.fetchWorkorderById(boqWorkDTO.getWorkorderId());
				if (!(workorder.getType().getId().equals(WorkorderTypes.Highway.getId())
						|| workorder.getType().getId().equals(WorkorderTypes.Structure.getId()))) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Inappropriate workorder type.");
				}
//				if (!workorder.getState().getId().equals(EngineStates.Draft.getValue()))
//					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Workorder is not in draft state.");
				if (workorder.getBoqWork() == null) {
					WorkorderBoqWork boqWork = setObject.workorderBoqWorkDtoToEntity(boqWorkDTO);
					if (boqWork.getLocations() == null || boqWork.getWorkScope() == null
							|| boqWork.getLocations().size() == 0) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Provide appropriate request.");
					}
					HashMap<Long, Set<Long>> typewiseStructures = new HashMap<Long, Set<Long>>();
					Set<Long> structureTypeIds = new LinkedHashSet<Long>();
					for (WorkorderBoqWorkLocation loc : boqWork.getLocations()) {
						if ((loc.getToChainageId() == null || loc.getFromChainageId() == null)
								&& loc.getStructureTypeId() == null) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Provide appropriate request.");
						}
						if (loc.getStructureTypeId() != null) {
							structureTypeIds.add(loc.getStructureTypeId());
							if (typewiseStructures.get(loc.getStructureTypeId()) == null) {
								typewiseStructures.put(loc.getStructureTypeId(), new HashSet<>());
							} else {
								if (loc.getStructureId() != null)
									typewiseStructures.get(loc.getStructureTypeId()).add(loc.getStructureId());
							}
						}
						loc.setIsActive(true);
						loc.setModifiedBy(boqWorkDTO.getModifiedBy());
						loc.setSiteId(boqWorkDTO.getSiteId());
						loc.setModifiedOn(new Date());
					}
					boqWork.setModifiedOn(new Date());
					boqWork.setWorkorderId(workorder.getId());
					boqWork.setVersion(0);
					boqWork.setIsActive(true);

					if (boqWork.getBoqWorkQty() != null && boqWork.getBoqWorkQty().size() > 0) {
						for (WorkorderBoqWorkQtyMapping wbwq : boqWork.getBoqWorkQty()) {
							if (wbwq.getBoq() == null || wbwq.getDescription() == null || wbwq.getUnit() == null
									|| wbwq.getUnit().getId() == null || wbwq.getVendorDescription() == null
									|| wbwq.getRate() == null || wbwq.getQuantity() == null
									|| wbwq.getRate().doubleValue() <= 0 || wbwq.getQuantity().doubleValue() < 0) {
								return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
										"Provide appropriate request (boqWorkQty).");
							}
							if (structureTypeIds.size() > 0 && wbwq.getStructureTypeId() == null) {
								return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
										"Provide appropriate request (structureTypeId in BOQs).");
							}
							wbwq.setIsActive(true);
							wbwq.setModifiedBy(boqWorkDTO.getModifiedBy());
							wbwq.setVersion(0);
							wbwq.setModifiedOn(new Date());
						}
					} else {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Provide appropriate request.");
					}

					Set<String> boqIdDesPairs = new LinkedHashSet<>();
					for (WorkorderBoqWorkQtyMapping wbwq : boqWork.getBoqWorkQty()) {
						boqIdDesPairs.add(wbwq.getBoq().getId() + "::-::" + wbwq.getStructureTypeId() + "::-::"
								+ wbwq.getStructureId() + "::-::" + wbwq.getDescription().trim());
					}
					for (String boqIdDesPair : boqIdDesPairs) {
						String[] idDes = boqIdDesPair.split("::-::");
						Long boqId = Long.valueOf(idDes[0]);
						String structureTypeIdString = idDes[1];
						String structureIdString = idDes[2];
						String boqDes = idDes[3].trim();
						List<String> boqDescriptionList = new ArrayList<>();
						Double amount = 0.0;
						for (WorkorderBoqWorkQtyMapping wbwq : boqWork.getBoqWorkQty()) {
							String wbwqStructureTypeIdString = wbwq.getStructureTypeId() != null
									? wbwq.getStructureTypeId().toString()
									: "null";
							String wbwqStructureIdString = wbwq.getStructureId() != null
									? wbwq.getStructureId().toString()
									: "null";
							if (wbwq.getBoq().getId().equals(boqId)
									&& wbwq.getDescription().trim().equalsIgnoreCase(boqDes)
									&& structureTypeIdString.equalsIgnoreCase(wbwqStructureTypeIdString)
									&& structureIdString.equalsIgnoreCase(wbwqStructureIdString)) {
								for (String boqDescription : boqDescriptionList) {
									if (boqDescription.equals(wbwq.getVendorDescription())) {
										return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
												"Provide appropriate request for BOQs with same SDB Code.");
									}
								}
								boqDescriptionList.add(wbwq.getVendorDescription());
								amount += (wbwq.getQuantity() * wbwq.getRate());
							}
						}
						if (structureTypeIds.size() > 0) {

							for (Long structureTypeId : structureTypeIds) {
								Long structureId = structureIdString.equals("null") ? null
										: Long.valueOf(structureIdString);
								if (!structureTypeId.toString().equals(structureTypeIdString)) {
									continue;
								}
								Double spareAmount = 0.0;
								Double spareQuantity = 0.0;
								List<StructureBoqQuantityMapping> sbqs = woBoqWorkDao
										.fetchActiveSbqsByStructureTypeIdAndStructureIdAndBoqId(structureTypeId,
												structureId, boqId, boqDes, boqWorkDTO.getSiteId());
								if (sbqs != null) {
									for (StructureBoqQuantityMapping sbq : sbqs) {
										spareAmount += (sbq.getRate() * sbq.getQuantity());
										spareQuantity += sbq.getQuantity();
									}
								}
								search.setSiteId(boqWorkDTO.getSiteId());
								search.setStructureTypeId(structureTypeId);
								search.setStructureId(structureId);
								List<WorkorderBoqWorkQtyMapping> woBoqWorkQtys = woBoqWorkDao
										.fetchStructureWoBoqWorkQtys(search);
								if (woBoqWorkQtys != null) {
									for (WorkorderBoqWorkQtyMapping obj : woBoqWorkQtys) {
										String wbwqStructureTypeIdString = obj.getStructureTypeId() != null
												? obj.getStructureTypeId().toString()
												: "null";
										String wbwqStructureIdString = obj.getStructureId() != null
												? obj.getStructureId().toString()
												: "null";
										if (wbwqStructureTypeIdString.equalsIgnoreCase(structureTypeIdString)
												&& wbwqStructureIdString.equalsIgnoreCase(structureIdString)
												&& obj.getBoq().getId().equals(boqId)
												&& obj.getDescription().trim().equalsIgnoreCase(boqDes)
												&& !obj.getBoqWork().getWorkorderId()
														.equals(boqWorkDTO.getWorkorderId())) {
											spareAmount -= (obj.getQuantity() * obj.getRate());
											spareQuantity -= obj.getQuantity();
										}
									}
								}
//								if (spareAmount < amount) {
//									StructureType structureType = woBoqWorkDao.fetchStructureTypeById(structureTypeId);
//									return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//											"Amount (" + amount + ") exceeds in compare to available amount ("
//													+ spareAmount + ") for BOQ having description : " + boqDes
//													+ ", for structure type " + structureType.getName());
//								}
							}
						} else {
							Double spareAmount = 0.0;
							Double spareQuantity = 0.0;

							List<WorkorderCategoryMapping> workorderCategories = woBasicDao
									.fetchWorkorderCategoriesByWorkorderId(search.getWorkorderId());
							Set<Long> workorderCategoryIds = new HashSet<>();
							if (workorderCategories != null) {
								for (WorkorderCategoryMapping wcm : workorderCategories) {
									workorderCategoryIds.add(wcm.getCategoryId());
								}
							}
							Chainage fromChainage = chainageDao
									.fetchById(boqWorkDTO.getLocations().get(0).getFromChainageId());
							Chainage toChainage = chainageDao
									.fetchById(boqWorkDTO.getLocations().get(0).getToChainageId());
							List<ChainageBoqQuantityMapping> cbqListAcc = woBoqWorkDao
									.fetchActiveCbqsBetweenChainageNumericValuesAndSiteIdAndWoCategories(
											fromChainage.getNumericChainageValue(),
											toChainage.getNumericChainageValue(), workorderCategoryIds,
											search.getSiteId());
							Set<Long> uniqueBoqIds = new HashSet<>();
							for (ChainageBoqQuantityMapping cbq : cbqListAcc) {
								uniqueBoqIds.add(cbq.getBoq().getId());
							}

							Map<Long, Double> boqChainageQuantityMapping = new HashMap<Long, Double>();
							List<ChainageBoqQuantityMapping> cbqList = new ArrayList<>();
							if (cbqList != null) {
								for (Long uniqueBoqId : uniqueBoqIds) {
									for (ChainageBoqQuantityMapping cbq : cbqListAcc) {
										if (uniqueBoqId.equals(cbq.getBoq().getId())) {
											cbqList.add(cbq);
										}
									}
									int counter = 0;
									Double area = 0.0;
									Double volume = 0.0;
									Boolean isVolumeBoq = false;
									for (ChainageBoqQuantityMapping cbq : cbqList) {
										if (cbq.getBoq().getUnit().getType().getId()
												.equals(UnitTypes.VOLUME.getTypeId())) {
											isVolumeBoq = true;
										}
										ChainageBoqQuantityMappingDTO obj = setObject.chainageBoqQtyMapEntityToDto(cbq);
										obj.setArea((obj.getLhsQuantity() != null ? obj.getLhsQuantity() : 0.0)
												+ (obj.getRhsQuantity() != null ? obj.getRhsQuantity() : 0.0));
										if (obj.getChainage() != null && obj.getChainage().getPrevious() != null) {
											Integer length = ((Integer
													.parseInt(obj.getChainage().getName().replaceAll("[^0-9]", "")))
													- (Integer.parseInt(obj.getChainage().getPrevious().getName()
															.replaceAll("[^0-9]", ""))));
											obj.setLength(length.doubleValue());
											if (counter == 0) {
												obj.setLength(0.0);
												obj.setVolume(0.0);
												area += obj.getArea();
											}
											if (counter > 0) {
												Double previousArea = (cbqList.get(counter - 1).getLhsQuantity() != null
														? cbqList.get(counter - 1).getLhsQuantity()
														: 0.0)
														+ (cbqList.get(counter - 1).getRhsQuantity() != null
																? cbqList.get(counter - 1).getRhsQuantity()
																: 0.0);
												Double currentArea = obj.getArea();
												area += currentArea;
												double meanArea = (previousArea + currentArea) / 2;
												if (cbq.getStructureRemark() == null
														|| cbq.getStructureRemark().isBlank()) {
													obj.setVolume(meanArea * obj.getLength());
													volume += meanArea * obj.getLength();
												} else {
													obj.setVolume(0.0);
												}
											}
										} else {
											obj.setLength(0.0);
											obj.setVolume(0.0);
											area += obj.getArea();
										}
										counter++;
									}
									if (isVolumeBoq) {
										boqChainageQuantityMapping.put(boqId, volume);
									} else {
										boqChainageQuantityMapping.put(boqId, area);
									}
								}
							}

							List<HighwayBoqMapping> hbqs = woBoqWorkDao
									.fetchHighwayBoqQtysByBoqId(boqWorkDTO.getSiteId(), boqId);

							if (hbqs != null) {
								for (HighwayBoqMapping hbq : hbqs) {
									if (boqChainageQuantityMapping.get(hbq.getBoq().getId()) != null)
										hbq.setQuantity(boqChainageQuantityMapping.get(hbq.getBoq().getId()));
								}
							}

							if (hbqs != null) {
								for (HighwayBoqMapping hbq : hbqs) {
									spareAmount += (hbq.getRate() * hbq.getQuantity());
									spareQuantity += hbq.getQuantity();
								}

								List<WorkorderBoqWorkQtyMapping> woBoqWorkQtys = woBoqWorkDao
										.fetchChainageWoBoqWorkQtys(search, null);
								if (woBoqWorkQtys != null) {
									for (WorkorderBoqWorkQtyMapping obj : woBoqWorkQtys) {
										if (obj.getBoq().getId().equals(boqId) && !obj.getBoqWork().getWorkorderId()
												.equals(boqWorkDTO.getWorkorderId())) {
											spareAmount -= (obj.getQuantity() * obj.getRate());
											spareQuantity -= obj.getQuantity();
										}
									}
								}
//								if (spareAmount < amount) {
//									return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//											"Amount (" + amount + ") exceeds in compare to available amount ("
//													+ spareAmount + ") for BOQs with same SDB Code : ");
//								}
							}
						}
					}

					Long id = woBoqWorkDao.saveWorkorderBoqWork(boqWork);
					boqWork.setId(id);
					workorder.setBoqWork(boqWork);
					workorder.setIsActive(true);
					workorderDao.addWorkorderBoqWork(new WorkorderBoqWork(id), workorder.getId());
					return new CustomResponse(Responses.SUCCESS.getCode(),
							((id != null && id.longValue() > 0L) ? id : "Already exists."),
							((id != null && id.longValue() > 0L) ? "Added." : "Already exists."));

				} else {
					WorkorderBoqWork savedBoqWork = workorder.getBoqWork();
					List<WorkorderBoqWorkQtyMapping> boqQtyMapsToDeactivate = new ArrayList<>();
					if (savedBoqWork.getBoqWorkQty() != null) {
						for (WorkorderBoqWorkQtyMapping boqWorkQty : savedBoqWork.getBoqWorkQty()) {
							boqWorkQty.setModifiedBy(boqWorkDTO.getModifiedBy());
							boqQtyMapsToDeactivate.add(boqWorkQty);
						}
						if (boqWorkDTO.getBoqWorkQty() != null) {
							for (int i = 0; i < boqQtyMapsToDeactivate.size(); i++) {
								WorkorderBoqWorkQtyMapping boqWork = boqQtyMapsToDeactivate.get(i);
								boolean isPresent = false;
								for (WorkorderBoqWorkQtyMappingDTO boqWorkQtyDTO : boqWorkDTO.getBoqWorkQty()) {
									if (boqWorkQtyDTO.getId() != null
											&& boqWorkQtyDTO.getId().equals(boqWork.getId())) {
										isPresent = true;
										break;
									}
								}
								if (isPresent) {
									boqQtyMapsToDeactivate.set(i, null);
								}
							}
							boqQtyMapsToDeactivate.removeAll(Collections.singleton(null));
						}
					}

					List<WorkorderBoqWorkLocation> boqLocsToDeactivate = new ArrayList<>();
					if (savedBoqWork.getLocations() != null) {
						for (WorkorderBoqWorkLocation boqWorkLoc : savedBoqWork.getLocations()) {
							boqWorkLoc.setModifiedBy(boqWorkDTO.getModifiedBy());
							boqLocsToDeactivate.add(boqWorkLoc);
						}
						if (boqWorkDTO.getLocations() != null) {
							for (int i = 0; i < boqLocsToDeactivate.size(); i++) {
								WorkorderBoqWorkLocation boqWorkLoc = boqLocsToDeactivate.get(i);
								boolean isPresent = false;
								for (WorkorderBoqWorkLocationDTO boqWorkLocDTO : boqWorkDTO.getLocations()) {
									if (boqWorkLocDTO.getId() != null
											&& boqWorkLocDTO.getId().equals(boqWorkLoc.getId())) {
										isPresent = true;
										break;
									}
								}
								if (isPresent) {
									boqLocsToDeactivate.set(i, null);
								}
							}
							boqLocsToDeactivate.removeAll(Collections.singleton(null));
						}
					}
					boqWorkDTO.setId(savedBoqWork.getId());
					WorkorderBoqWork boqWorkToUpdate = setObject.workorderBoqWorkDtoToEntity(boqWorkDTO);
					savedBoqWork.setModifiedOn(new Date());
					savedBoqWork.setModifiedBy(boqWorkDTO.getModifiedBy());
					savedBoqWork.setIsActive(true);
					savedBoqWork.setAnnexureNote(boqWorkDTO.getAnnexureNote());
					HashMap<Long, Set<Long>> typewiseStructures = new HashMap<Long, Set<Long>>();
					Set<Long> structureTypeIds = new LinkedHashSet<Long>();
					if (boqWorkToUpdate.getLocations() == null || boqWorkToUpdate.getWorkScope() == null
							|| boqWorkToUpdate.getLocations().size() == 0) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Provide appropriate request.");
					}
					for (WorkorderBoqWorkLocation loc : boqWorkToUpdate.getLocations()) {
						if ((loc.getToChainageId() == null || loc.getFromChainageId() == null)
								&& loc.getStructureTypeId() == null) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Provide appropriate request.");
						}
						if (loc.getStructureTypeId() != null) {
							structureTypeIds.add(loc.getStructureTypeId());
							if (typewiseStructures.get(loc.getStructureTypeId()) == null) {
								typewiseStructures.put(loc.getStructureTypeId(), new HashSet<>());
							} else {
								if (loc.getStructureId() != null)
									typewiseStructures.get(loc.getStructureTypeId()).add(loc.getStructureId());
							}
						}
						loc.setIsActive(true);
						loc.setModifiedBy(boqWorkDTO.getModifiedBy());
						loc.setSiteId(boqWorkDTO.getSiteId());
						loc.setModifiedOn(new Date());
					}

					if (boqWorkToUpdate.getBoqWorkQty() != null && boqWorkToUpdate.getBoqWorkQty().size() > 0) {
						for (WorkorderBoqWorkQtyMapping wbwq : boqWorkToUpdate.getBoqWorkQty()) {
							if (wbwq.getBoq() == null || wbwq.getDescription() == null
									|| wbwq.getVendorDescription() == null || wbwq.getRate() == null
									|| wbwq.getUnit() == null || wbwq.getUnit().getId() == null
									|| wbwq.getQuantity() == null || wbwq.getRate().doubleValue() <= 0
									|| wbwq.getQuantity().doubleValue() < 0) {
								return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
										"Provide valid request body (boqWorkQty).");
							}
							if (structureTypeIds.size() > 0 && wbwq.getStructureTypeId() == null) {
								return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
										"Provide appropriate request (structureTypeId in BOQs).");
							}
							wbwq.setIsActive(true);
							wbwq.setModifiedBy(boqWorkDTO.getModifiedBy());
							wbwq.setVersion(0);
							wbwq.setModifiedOn(new Date());
						}
					} else {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Provide appropriate request body.");
					}

					Set<String> boqIdDesPairs = new LinkedHashSet<>();
					for (WorkorderBoqWorkQtyMappingDTO wbwq : boqWorkDTO.getBoqWorkQty()) {
						boqIdDesPairs.add(wbwq.getBoq().getId() + "::-::" + wbwq.getStructureTypeId() + "::-::"
								+ wbwq.getStructureId() + "::-::" + wbwq.getDescription().trim());
					}
					for (String boqIdDesPair : boqIdDesPairs) {
						String[] idDes = boqIdDesPair.split("::-::");
						Long boqId = Long.valueOf(idDes[0]);
						String structureTypeIdString = idDes[1];
						String structureIdString = idDes[2];
						String boqDes = idDes[3].trim();
						List<String> boqDescriptionList = new ArrayList<>();
						Double amount = 0.0;
						for (WorkorderBoqWorkQtyMappingDTO wbwq : boqWorkDTO.getBoqWorkQty()) {
							String wbwqStructureTypeIdString = wbwq.getStructureTypeId() != null
									? wbwq.getStructureTypeId().toString()
									: "null";
							String wbwqStructureIdString = wbwq.getStructureId() != null
									? wbwq.getStructureId().toString()
									: "null";
							if (wbwq.getBoq().getId().equals(boqId)
									&& wbwq.getDescription().trim().equalsIgnoreCase(boqDes)
									&& structureTypeIdString.equalsIgnoreCase(wbwqStructureTypeIdString)
									&& structureIdString.equalsIgnoreCase(wbwqStructureIdString)) {

								for (String boqDescription : boqDescriptionList) {
									if (boqDescription.equals(wbwq.getVendorDescription())) {
										return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
												"Provide appropriate request body for same boq group.");
									}
								}
								boqDescriptionList.add(wbwq.getVendorDescription());
								amount += (wbwq.getQuantity() * wbwq.getRate());
							}
						}
						if (structureTypeIds.size() > 0) {

							for (Long structureTypeId : structureTypeIds) {
								Long structureId = structureIdString.equals("null") ? null
										: Long.valueOf(structureIdString);
								if (!structureTypeId.toString().equals(structureTypeIdString)) {
									continue;
								}
								Double spareAmount = 0.0;
								Double spareQuantity = 0.0;
								List<StructureBoqQuantityMapping> sbqs = woBoqWorkDao
										.fetchActiveSbqsByStructureTypeIdAndStructureIdAndBoqId(structureTypeId,
												structureId, boqId, boqDes, boqWorkDTO.getSiteId());
								if (sbqs != null) {
									for (StructureBoqQuantityMapping sbq : sbqs) {
										spareAmount += (sbq.getRate() * sbq.getQuantity());
										spareQuantity += sbq.getQuantity();
									}
								}
								search.setSiteId(boqWorkDTO.getSiteId());
								search.setStructureTypeId(structureTypeId);
								search.setStructureId(structureId);
								List<WorkorderBoqWorkQtyMapping> woBoqWorkQtys = woBoqWorkDao
										.fetchStructureWoBoqWorkQtys(search);
								if (woBoqWorkQtys != null) {
									for (WorkorderBoqWorkQtyMapping obj : woBoqWorkQtys) {
										String wbwqStructureTypeIdString = obj.getStructureTypeId() != null
												? obj.getStructureTypeId().toString()
												: "null";
										String wbwqStructureIdString = obj.getStructureId() != null
												? obj.getStructureId().toString()
												: "null";
										if (wbwqStructureTypeIdString.equalsIgnoreCase(structureTypeIdString)
												&& wbwqStructureIdString.equalsIgnoreCase(structureIdString)
												&& obj.getBoq().getId().equals(boqId)
												&& obj.getDescription().trim().equalsIgnoreCase(boqDes)
												&& !obj.getBoqWork().getWorkorderId()
														.equals(boqWorkDTO.getWorkorderId())) {
											spareAmount -= (obj.getQuantity() * obj.getRate());
											spareQuantity -= obj.getQuantity();
										}
									}
								}
//								if (spareAmount < amount) {
//									StructureType structureType = woBoqWorkDao.fetchStructureTypeById(structureTypeId);
//									return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//											"Amount (" + amount + ") exceeds in compare to available amount ("
//													+ spareAmount + ") for BOQ having description : " + boqDes
//													+ ", for structure type " + structureType.getName());
//								}
							}
						} else {
							Double spareAmount = 0.0;
							Double spareQuantity = 0.0;
							List<WorkorderCategoryMapping> workorderCategories = woBasicDao
									.fetchWorkorderCategoriesByWorkorderId(search.getWorkorderId());
							Set<Long> workorderCategoryIds = new HashSet<>();
							if (workorderCategories != null) {
								for (WorkorderCategoryMapping wcm : workorderCategories) {
									workorderCategoryIds.add(wcm.getCategoryId());
								}
							}
							Chainage fromChainage = chainageDao
									.fetchById(boqWorkDTO.getLocations().get(0).getFromChainageId());
							Chainage toChainage = chainageDao
									.fetchById(boqWorkDTO.getLocations().get(0).getToChainageId());
							List<ChainageBoqQuantityMapping> cbqListAcc = woBoqWorkDao
									.fetchActiveCbqsBetweenChainageNumericValuesAndSiteIdAndWoCategories(
											fromChainage.getNumericChainageValue(),
											toChainage.getNumericChainageValue(), workorderCategoryIds,
											search.getSiteId());
							Set<Long> uniqueBoqIds = new HashSet<>();
							for (ChainageBoqQuantityMapping cbq : cbqListAcc) {
								uniqueBoqIds.add(cbq.getBoq().getId());
							}

							Map<Long, Double> boqChainageQuantityMapping = new HashMap<Long, Double>();
							List<ChainageBoqQuantityMapping> cbqList = new ArrayList<>();
							if (cbqList != null) {
								for (Long uniqueBoqId : uniqueBoqIds) {
									for (ChainageBoqQuantityMapping cbq : cbqListAcc) {
										if (uniqueBoqId.equals(cbq.getBoq().getId())) {
											cbqList.add(cbq);
										}
									}
									int counter = 0;
									Double area = 0.0;
									Double volume = 0.0;
									Boolean isVolumeBoq = false;
									for (ChainageBoqQuantityMapping cbq : cbqList) {
										if (cbq.getBoq().getUnit().getType().getId()
												.equals(UnitTypes.VOLUME.getTypeId())) {
											isVolumeBoq = true;
										}
										ChainageBoqQuantityMappingDTO obj = setObject.chainageBoqQtyMapEntityToDto(cbq);
										obj.setArea((obj.getLhsQuantity() != null ? obj.getLhsQuantity() : 0.0)
												+ (obj.getRhsQuantity() != null ? obj.getRhsQuantity() : 0.0));
										if (obj.getChainage() != null && obj.getChainage().getPrevious() != null) {
											Integer length = ((Integer
													.parseInt(obj.getChainage().getName().replaceAll("[^0-9]", "")))
													- (Integer.parseInt(obj.getChainage().getPrevious().getName()
															.replaceAll("[^0-9]", ""))));
											obj.setLength(length.doubleValue());
											if (counter == 0) {
												obj.setLength(0.0);
												obj.setVolume(0.0);
												area += obj.getArea();
											}
											if (counter > 0) {
												Double previousArea = (cbqList.get(counter - 1).getLhsQuantity() != null
														? cbqList.get(counter - 1).getLhsQuantity()
														: 0.0)
														+ (cbqList.get(counter - 1).getRhsQuantity() != null
																? cbqList.get(counter - 1).getRhsQuantity()
																: 0.0);
												Double currentArea = obj.getArea();
												area += currentArea;
												double meanArea = (previousArea + currentArea) / 2;
												if (cbq.getStructureRemark() == null
														|| cbq.getStructureRemark().isBlank()) {
													obj.setVolume(meanArea * obj.getLength());
													volume += meanArea * obj.getLength();
												} else {
													obj.setVolume(0.0);
												}
											}
										} else {
											obj.setLength(0.0);
											obj.setVolume(0.0);
											area += obj.getArea();
										}
										counter++;
									}
									if (isVolumeBoq) {
										boqChainageQuantityMapping.put(boqId, volume);
									} else {
										boqChainageQuantityMapping.put(boqId, area);
									}
								}
							}

							List<HighwayBoqMapping> hbqs = woBoqWorkDao
									.fetchHighwayBoqQtysByBoqId(boqWorkDTO.getSiteId(), boqId);

							if (hbqs != null) {
								for (HighwayBoqMapping hbq : hbqs) {
									if (boqChainageQuantityMapping.get(hbq.getBoq().getId()) != null)
										hbq.setQuantity(boqChainageQuantityMapping.get(hbq.getBoq().getId()));
								}
							}
							if (hbqs != null) {
								for (HighwayBoqMapping hbq : hbqs) {
									spareAmount += (hbq.getRate() * hbq.getQuantity());
									spareQuantity += hbq.getQuantity();
								}

								List<WorkorderBoqWorkQtyMapping> woBoqWorkQtys = woBoqWorkDao
										.fetchChainageWoBoqWorkQtys(search, null);
								if (woBoqWorkQtys != null) {
									for (WorkorderBoqWorkQtyMapping obj : woBoqWorkQtys) {
										if (obj.getBoq().getId().equals(boqId) && !obj.getBoqWork().getWorkorderId()
												.equals(boqWorkDTO.getWorkorderId())) {
											spareAmount -= (obj.getQuantity() * obj.getRate());
											spareQuantity -= obj.getQuantity();
										}
									}
								}
//								if (spareAmount < amount) {
//									return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//											"Amount (" + amount + ") exceeds in compare to available amount ("
//													+ spareAmount + ") for BOQs with same SDB Code");
//								}
							}
						}
					}
					savedBoqWork.setWorkScope(boqWorkToUpdate.getWorkScope());
					savedBoqWork.setLocations(boqWorkToUpdate.getLocations());
					savedBoqWork.setBoqWorkQty(boqWorkToUpdate.getBoqWorkQty());
					Boolean result = woBoqWorkDao.updateWorkorderBoqWork(savedBoqWork);
					if (result) {
						woBoqWorkDao.forceDeactivateBoqWorkLocations(boqLocsToDeactivate);
						woBoqWorkDao.forceDeactivateBoqWorkQtys(boqQtyMapsToDeactivate);
					}
					return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
				}
			}
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide appropriate workorderId.");

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null, "Provide appropriate request body.");
		}
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public CustomResponse getWorkorderBoqWorkById(SearchDTO search) {

		try {
			Workorder workorder = workorderDao.fetchWorkorderById(search.getWorkorderId());
			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide appropriate workorderId.");
			WorkorderBoqWork boqWork = workorder.getBoqWork();
			List<BoqItem> boqs = boqDao.fetchBoqItems(search);
			if (boqWork == null && search.getNonExistRender() != null && search.getNonExistRender()) {
				List<WorkorderCategoryMapping> workorderCategories = woBasicDao
						.fetchWorkorderCategoriesByWorkorderId(search.getWorkorderId());
				Set<Long> workorderCategoryIds = new HashSet<>();
				if (workorderCategories != null) {
					for (WorkorderCategoryMapping wcm : workorderCategories) {
						workorderCategoryIds.add(wcm.getCategoryId());
					}
				}
				WorkorderType woType = workorder.getType();
				if (woType != null) {
					if (woType.getId().intValue() == WorkorderTypes.Highway.getId()) {
						if (search.getLocations() == null || search.getLocations().size() == 0) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Provide set of both chainage id's...");
						}
						for (WorkorderBoqWorkLocationDTO location : search.getLocations()) {
							if (location.getFromChainageId() == null || location.getToChainageId() == null) {
								return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
										"Provide chainage range ids in locations.");
							}
						}
						Chainage fromChainage = chainageDao.fetchById(search.getLocations().get(0).getFromChainageId());
						Chainage toChainage = chainageDao.fetchById(search.getLocations().get(0).getToChainageId());
						List<ChainageBoqQuantityMapping> cbqListAcc = woBoqWorkDao
								.fetchActiveCbqsBetweenChainageNumericValuesAndSiteIdAndWoCategories(
										fromChainage.getNumericChainageValue(), toChainage.getNumericChainageValue(),
										workorderCategoryIds, search.getSiteId());
						Set<Long> uniqueBoqIds = new HashSet<>();
						for (ChainageBoqQuantityMapping cbq : cbqListAcc) {
							uniqueBoqIds.add(cbq.getBoq().getId());
						}

						Map<Long, Double> boqChainageQuantityMapping = new HashMap<Long, Double>();
						List<ChainageBoqQuantityMapping> cbqList = new ArrayList<>();
						if (cbqList != null) {
							for (Long boqId : uniqueBoqIds) {
								for (ChainageBoqQuantityMapping cbq : cbqListAcc) {
									if (boqId.equals(cbq.getBoq().getId())) {
										cbqList.add(cbq);
									}
								}
								int counter = 0;
								Double area = 0.0;
								Double volume = 0.0;
								Boolean isVolumeBoq = false;
								for (ChainageBoqQuantityMapping cbq : cbqList) {
									if (cbq.getBoq().getUnit().getType().getId().equals(UnitTypes.VOLUME.getTypeId())) {
										isVolumeBoq = true;
									}
									ChainageBoqQuantityMappingDTO obj = setObject.chainageBoqQtyMapEntityToDto(cbq);
									obj.setArea((obj.getLhsQuantity() != null ? obj.getLhsQuantity() : 0.0)
											+ (obj.getRhsQuantity() != null ? obj.getRhsQuantity() : 0.0));
									if (obj.getChainage() != null && obj.getChainage().getPrevious() != null) {
										Integer length = ((Integer
												.parseInt(obj.getChainage().getName().replaceAll("[^0-9]", "")))
												- (Integer.parseInt(obj.getChainage().getPrevious().getName()
														.replaceAll("[^0-9]", ""))));
										obj.setLength(length.doubleValue());
										if (counter == 0) {
											obj.setLength(0.0);
											obj.setVolume(0.0);
											area += obj.getArea();
										}
										if (counter > 0) {
											Double previousArea = (cbqList.get(counter - 1).getLhsQuantity() != null
													? cbqList.get(counter - 1).getLhsQuantity()
													: 0.0)
													+ (cbqList.get(counter - 1).getRhsQuantity() != null
															? cbqList.get(counter - 1).getRhsQuantity()
															: 0.0);
											Double currentArea = obj.getArea();
											area += currentArea;
											double meanArea = (previousArea + currentArea) / 2;
											if (cbq.getStructureRemark() == null
													|| cbq.getStructureRemark().isBlank()) {
												obj.setVolume(meanArea * obj.getLength());
												volume += meanArea * obj.getLength();
											} else {
												obj.setVolume(0.0);
											}
										}
									} else {
										obj.setLength(0.0);
										obj.setVolume(0.0);
										area += obj.getArea();
									}
									counter++;
								}
								if (isVolumeBoq) {
									boqChainageQuantityMapping.put(boqId, volume);
								} else {
									boqChainageQuantityMapping.put(boqId, area);
								}
							}
						}
						search.setIdsArrSet(workorderCategoryIds);
						List<HighwayBoqMapping> hbqs = hbmDao.fetchHbqsByCategories(search);
						if (hbqs != null) {
							for (HighwayBoqMapping hbq : hbqs) {
								if (boqChainageQuantityMapping.get(hbq.getBoq().getId()) != null)
									hbq.setQuantity(boqChainageQuantityMapping.get(hbq.getBoq().getId()));
							}
						}
						List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWorkDao
								.fetchSiteChainagesWoBoqWorkQtys(search);
						if (woBoqQtys != null && hbqs != null) {
							for (HighwayBoqMapping hbq : hbqs) {
								Double workorderQuantity = 0.0;
								for (WorkorderBoqWorkQtyMapping obj : woBoqQtys) {
									if (obj.getBoq().getId().equals(hbq.getBoq().getId())) {
										workorderQuantity += obj.getQuantity();
									}
								}
								hbq.setQuantity(hbq.getQuantity().doubleValue() - workorderQuantity.doubleValue());
							}
						}

						ChainageBoqsRenderDTO result = setObject.renderChildParentHighwayBoqsInCbqFormat(hbqs, boqs);
						return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
					} else if (woType.getId().intValue() == WorkorderTypes.Structure.getId()) {
						Long structureTypeId = null;
						Long structureId = null;
						if (search.getLocations() == null || search.getLocations().size() == 0)
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Provide set of locations.");
						for (WorkorderBoqWorkLocationDTO location : search.getLocations()) {
							if (location.getStructureTypeId() == null) {
								return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
										"Provide structure type id in locations.");
							}
							structureTypeId = location.getStructureTypeId();
							structureId = location.getStructureId();
							break;
						}
						search.setStructureTypeId(structureTypeId);
						List<StructureBoqQuantityMapping> sbqs = woBoqWorkDao
								.fetchActiveSbqsByStructureTypeIdAndStructureIdAndCategoryIds(structureTypeId,
										structureId, workorderCategoryIds, search.getSiteId());
						search.setStructureId(structureId);
						List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWorkDao.fetchStructureWoBoqWorkQtys(search);
						List<StructureBoqQuantityMapping> sbqsAfterAccumulationOfQuantitiesAndWeightedRate = new ArrayList<>();
						if (woBoqQtys != null && sbqs != null) {

							HashSet<String> boqIdDescriptionUniquePairFlags = new HashSet<>();

							for (StructureBoqQuantityMapping sbq : sbqs) {
								Long boqId = sbq.getBoq().getId();
								String boqDescription = sbq.getDescription();
								String boqIdDescriptionUniquePairPattern = boqId + "/" + boqDescription;

								if (boqIdDescriptionUniquePairFlags.contains(boqIdDescriptionUniquePairPattern)) {
									continue;
								}
								boqIdDescriptionUniquePairFlags.add(boqIdDescriptionUniquePairPattern);
								Double rateWeighted = null;
								Double quantityAccumulated = 0.0;
								for (StructureBoqQuantityMapping sbqItr : sbqs) {
									if (boqId.equals(sbqItr.getBoq().getId())
											&& boqDescription.trim().equalsIgnoreCase(sbqItr.getDescription().trim())) {
										if (rateWeighted == null) {
											rateWeighted = sbqItr.getRate();
										} else {
											rateWeighted = (rateWeighted + sbqItr.getRate()) / 2;
										}
										quantityAccumulated += sbqItr.getQuantity();
									}
								}
								sbq.setRate(rateWeighted == null ? 0.0 : rateWeighted);
								sbq.setQuantity(quantityAccumulated);
								sbqsAfterAccumulationOfQuantitiesAndWeightedRate.add(sbq);
							}

							for (StructureBoqQuantityMapping sbq : sbqsAfterAccumulationOfQuantitiesAndWeightedRate) {
								for (WorkorderBoqWorkQtyMapping obj : woBoqQtys) {
									if (obj.getBoq().getId().equals(sbq.getBoq().getId()) && obj.getDescription().trim()
											.equalsIgnoreCase(sbq.getDescription().trim())) {
										sbq.setQuantity(
												sbq.getQuantity().doubleValue() - obj.getQuantity().doubleValue());
										if (sbq.getQuantity().doubleValue() < 0.0) {
											sbq.setQuantity(0.0);
										}
									}
								}
							}
						}
						Object result = setObject
								.renderChildParentSbqs(sbqsAfterAccumulationOfQuantitiesAndWeightedRate).getData();
						List<WorkorderStructureTypeBoqResponse> resultResponse = new ArrayList<>();
						WorkorderStructureTypeBoqResponse structureBoq;
						if (structureId != null) {
							Structure structure = structureDao.fetchStructureById(structureId);
							structureBoq = new WorkorderStructureTypeBoqResponse(structure.getType().getId(),
									structure.getType().getName(), structure.getId(), structure.getName(), result);
						} else {
							StructureType structureType = woBoqWorkDao.fetchStructureTypeById(structureTypeId);
							structureBoq = new WorkorderStructureTypeBoqResponse(structureTypeId,
									structureType.getName(), null, null, result);
						}

						resultResponse.add(structureBoq);
						return new CustomResponse(Responses.SUCCESS.getCode(), resultResponse,
								Responses.SUCCESS.toString());
					}
				}
			} else if (boqWork == null && (search.getNonExistRender() == null
					|| (search.getNonExistRender() != null && !search.getNonExistRender()))) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), false,
						"Workorder Boq Work has not defined for this workorder yet.");
			}
			List<WorkorderBoqWorkQtyMapping> boqWorkQtysSaved = boqWork.getBoqWorkQty();
			List<WorkorderBoqWorkQtyMapping> boqWorkQtys = new ArrayList<WorkorderBoqWorkQtyMapping>();
			HashMap<Long, Set<Long>> typewiseStructures = new HashMap<>();
			if (boqWorkQtysSaved != null) {
				for (WorkorderBoqWorkQtyMapping boqWorkQty : boqWorkQtysSaved) {
					if (boqWorkQty.getIsActive()) {
						boqWorkQtys.add(boqWorkQty);
						if (boqWorkQty.getStructureTypeId() != null) {
							if (typewiseStructures.get(boqWorkQty.getStructureTypeId()) == null) {
								typewiseStructures.put(boqWorkQty.getStructureTypeId(), new HashSet<>());
							} else {
								typewiseStructures.get(boqWorkQty.getStructureTypeId())
										.add(boqWorkQty.getStructureId());
							}
						}
					}

				}
			}

			Map<Integer, Object> otherBoqData = new HashMap<>();
			boolean isStructure = false;
			List<MinimalWorkorderBoqLocationDTO> locations = new ArrayList<>();
			Long fromChainageId = null;
			Long toChainageId = null;
			if (boqWork.getLocations() != null) {
				for (WorkorderBoqWorkLocation loc : boqWork.getLocations()) {
					if (loc.getIsActive()) {
						MinimalObjectDTO fromChainageMinDTO = null;
						MinimalObjectDTO toChainageMinDTO = null;
						MinimalObjectDTO structureMinDTO = null;
						if (loc.getFromChainageId() != null && loc.getToChainageId() != null) {
							fromChainageId = loc.getFromChainageId();
							toChainageId = loc.getToChainageId();
							fromChainageMinDTO = new MinimalObjectDTO(loc.getFromChainage().getId(),
									loc.getFromChainage().getName());
							toChainageMinDTO = new MinimalObjectDTO(loc.getToChainage().getId(),
									loc.getToChainage().getName());
						}

						if (loc.getStructureTypeId() != null) {
							if (typewiseStructures.get(loc.getStructureTypeId()) == null) {
								continue;
							}
							if (loc.getStructureId() != null && typewiseStructures.get(loc.getStructureTypeId())
									.contains(loc.getStructureId())) {

								structureMinDTO = new MinimalObjectDTO(loc.getStructureType().getId(),
										loc.getStructureType().getName(), loc.getStructureId(),
										loc.getStructure().getName(), null);
								structureMinDTO.setTypeId(loc.getStructureType().getId().intValue());
								isStructure = true;
							} else {
								structureMinDTO = new MinimalObjectDTO(loc.getStructureType().getId(),
										loc.getStructureType().getName());
								structureMinDTO.setTypeId(loc.getStructureType().getId().intValue());
								isStructure = true;
							}
						}
						locations.add(new MinimalWorkorderBoqLocationDTO(loc.getId(), fromChainageMinDTO,
								toChainageMinDTO, structureMinDTO));
					}
				}
			}
			List<WorkorderBoqWorkParentChildDTO> pcBoqs = null;
			WorkorderBoqWorkRenderDTO renderDTO = null;
			boolean hasWoBoqs = false;
			if (isStructure) {
				List<WorkorderStructureTypeBoqResponse> structureBoqsList = new ArrayList<>();
				for (WorkorderBoqWorkLocation loc : boqWork.getLocations()) {
					if (loc.getIsActive()) {
						List<WorkorderBoqWorkQtyMapping> woStructureBoq = new ArrayList<>();
						for (WorkorderBoqWorkQtyMapping obj : boqWorkQtys) {
							if (obj.getStructureTypeId() != null
									&& obj.getStructureTypeId().equals(loc.getStructureTypeId())
									&& ((obj.getStructureId() == null && loc.getStructureId() == null)
											|| (obj.getStructureId() != null && loc.getStructureId() != null
													&& obj.getStructureId().equals(loc.getStructureId())))) {
								woStructureBoq.add(obj);
							}
						}
						if (woStructureBoq.size() == 0) {
							continue;
						}
						List<WorkorderBoqWorkParentChildDTO> psBoqs = setObject
								.renderParentChildWoBoqWork(woStructureBoq, boqs);
						if (psBoqs != null) {
							hasWoBoqs = true;
						}
						WorkorderStructureTypeBoqResponse structureBoqs = new WorkorderStructureTypeBoqResponse(
								loc.getStructureTypeId(), loc.getStructureType().getName(), loc.getStructureId(),
								loc.getStructure() != null ? loc.getStructure().getName() : null, psBoqs);
						structureBoqsList.add(structureBoqs);
					}
				}
				renderDTO = new WorkorderBoqWorkRenderDTO(boqWork.getId(), workorder.getId(), locations, null,
						boqWork.getWorkScope(), boqWork.getAnnexureNote());
				renderDTO.setStructureBoqs(structureBoqsList);
			} else {
				pcBoqs = setObject.renderParentChildWoBoqWork(boqWorkQtys, boqs);
				if (pcBoqs != null) {
					hasWoBoqs = true;
				}
				renderDTO = new WorkorderBoqWorkRenderDTO(boqWork.getId(), workorder.getId(), locations, pcBoqs,
						boqWork.getWorkScope(), boqWork.getAnnexureNote());
			}

			List<WorkorderCategoryMapping> workorderCategories = woBasicDao
					.fetchWorkorderCategoriesByWorkorderId(search.getWorkorderId());
			Set<Long> workorderCategoryIds = new HashSet<>();
			if (workorderCategories != null) {
				for (WorkorderCategoryMapping wcm : workorderCategories) {
					workorderCategoryIds.add(wcm.getCategoryId());
				}
			}
			WorkorderType woType = workorder.getType();
			if (woType != null) {
				if (woType.getId().intValue() == WorkorderTypes.Highway.getId()) {

					Chainage fromChainage = chainageDao.fetchById(fromChainageId);
					Chainage toChainage = chainageDao.fetchById(toChainageId);
					List<ChainageBoqQuantityMapping> cbqListAcc = woBoqWorkDao
							.fetchActiveCbqsBetweenChainageNumericValuesAndSiteIdAndWoCategories(
									fromChainage.getNumericChainageValue(), toChainage.getNumericChainageValue(),
									workorderCategoryIds, search.getSiteId());
					Set<Long> uniqueBoqIds = new HashSet<>();
					for (ChainageBoqQuantityMapping cbq : cbqListAcc) {
						uniqueBoqIds.add(cbq.getBoq().getId());
					}

					Map<Long, Double> boqChainageQuantityMapping = new HashMap<Long, Double>();
					List<ChainageBoqQuantityMapping> cbqList = new ArrayList<>();
					if (cbqList != null) {
						for (Long boqId : uniqueBoqIds) {
							for (ChainageBoqQuantityMapping cbq : cbqListAcc) {
								if (boqId.equals(cbq.getBoq().getId())) {
									cbqList.add(cbq);
								}
							}
							int counter = 0;
							Double area = 0.0;
							Double volume = 0.0;
							Boolean isVolumeBoq = false;
							for (ChainageBoqQuantityMapping cbq : cbqList) {
								if (cbq.getBoq().getUnit().getType().getId().equals(UnitTypes.VOLUME.getTypeId())) {
									isVolumeBoq = true;
								}
								ChainageBoqQuantityMappingDTO obj = setObject.chainageBoqQtyMapEntityToDto(cbq);
								obj.setArea((obj.getLhsQuantity() != null ? obj.getLhsQuantity() : 0.0)
										+ (obj.getRhsQuantity() != null ? obj.getRhsQuantity() : 0.0));
								if (obj.getChainage() != null && obj.getChainage().getPrevious() != null) {
									Integer length = ((Integer
											.parseInt(obj.getChainage().getName().replaceAll("[^0-9]", "")))
											- (Integer.parseInt(obj.getChainage().getPrevious().getName()
													.replaceAll("[^0-9]", ""))));
									obj.setLength(length.doubleValue());
									if (counter == 0) {
										obj.setLength(0.0);
										obj.setVolume(0.0);
										area += obj.getArea();
									}
									if (counter > 0) {
										Double previousArea = (cbqList.get(counter - 1).getLhsQuantity() != null
												? cbqList.get(counter - 1).getLhsQuantity()
												: 0.0)
												+ (cbqList.get(counter - 1).getRhsQuantity() != null
														? cbqList.get(counter - 1).getRhsQuantity()
														: 0.0);
										Double currentArea = obj.getArea();
										area += currentArea;
										double meanArea = (previousArea + currentArea) / 2;
										if (cbq.getStructureRemark() == null || cbq.getStructureRemark().isBlank()) {
											obj.setVolume(meanArea * obj.getLength());
											volume += meanArea * obj.getLength();
										} else {
											obj.setVolume(0.0);
										}
									}
								} else {
									obj.setLength(0.0);
									obj.setVolume(0.0);
									area += obj.getArea();
								}
								counter++;
							}
							if (isVolumeBoq) {
								boqChainageQuantityMapping.put(boqId, volume);
							} else {
								boqChainageQuantityMapping.put(boqId, area);
							}
						}
					}

					search.setIdsArrSet(workorderCategoryIds);
					List<HighwayBoqMapping> hbqs = hbmDao.fetchHbqsByCategories(search);
					if (hbqs != null) {
						for (HighwayBoqMapping hbq : hbqs) {
							if (boqChainageQuantityMapping.get(hbq.getBoq().getId()) != null)
								hbq.setQuantity(boqChainageQuantityMapping.get(hbq.getBoq().getId()));
						}
					}
					List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWorkDao.fetchSiteChainagesWoBoqWorkQtys(search);
					if (woBoqQtys != null && hbqs != null) {
						for (HighwayBoqMapping hbq : hbqs) {
							Double workorderQuantity = 0.0;
							Double workorderAmount = 0.0;
							for (WorkorderBoqWorkQtyMapping obj : woBoqQtys) {
								if (obj.getBoq().getId().equals(hbq.getBoq().getId())) {
									workorderQuantity += obj.getQuantity();
									workorderAmount += (obj.getRate() * obj.getQuantity());
								}
							}
							Double leftoverAmountBasedQuantity = 0.0;
							if (hbq.getRate().doubleValue() > 0.0) {
								leftoverAmountBasedQuantity = ((hbq.getQuantity().doubleValue()
										* hbq.getRate().doubleValue()) - workorderAmount) / hbq.getRate().doubleValue();
							} else {
								leftoverAmountBasedQuantity = hbq.getQuantity().doubleValue()
										- workorderQuantity.doubleValue();
							}
							hbq.setQuantity(leftoverAmountBasedQuantity);
							if (hbq.getQuantity().doubleValue() < 0.0) {
								hbq.setQuantity(0.0);
							}
						}
					}
					ChainageBoqsRenderDTO result = setObject.renderChildParentHighwayBoqsInCbqFormat(hbqs, boqs);
					otherBoqData.put(0, result.getData());
				} else if (woType.getId().intValue() == WorkorderTypes.Structure.getId()) {
					List<WorkorderStructureTypeBoqResponse> structureBoqResponse = new ArrayList<>();
					for (WorkorderBoqWorkLocation location : boqWork.getLocations()) {
						if (location.getIsActive()) {
							search.setStructureTypeId(location.getStructureTypeId());
							search.setStructureId(location.getStructureId());
							List<StructureBoqQuantityMapping> sbqs = woBoqWorkDao
									.fetchActiveSbqsByStructureTypeIdAndStructureIdAndCategoryIds(
											location.getStructureTypeId(), location.getStructureId(),
											workorderCategoryIds, search.getSiteId());
							List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWorkDao
									.fetchStructureWoBoqWorkQtys(search);
							List<StructureBoqQuantityMapping> sbqsAfterAccumulationOfQuantitiesAndWeightedRate = new ArrayList<>();

							if (woBoqQtys == null || woBoqQtys.size() <= 0) {
								continue;
							}
							if (woBoqQtys != null && sbqs != null) {

								HashSet<String> boqIdDescriptionUniquePairFlags = new HashSet<>();

								for (StructureBoqQuantityMapping sbq : sbqs) {
									Long boqId = sbq.getBoq().getId();
									String boqDescription = sbq.getDescription();
									String boqIdDescriptionUniquePairPattern = boqId + "/" + boqDescription;

									if (boqIdDescriptionUniquePairFlags.contains(boqIdDescriptionUniquePairPattern)) {
										continue;
									}
									boqIdDescriptionUniquePairFlags.add(boqIdDescriptionUniquePairPattern);
									Double rateWeighted = null;
									Double quantityAccumulated = 0.0;
									for (StructureBoqQuantityMapping sbqItr : sbqs) {
										if (boqId.equals(sbqItr.getBoq().getId()) && boqDescription.trim()
												.equalsIgnoreCase(sbqItr.getDescription().trim())) {
											if (rateWeighted == null) {
												rateWeighted = sbqItr.getRate();
											} else {
												rateWeighted = (rateWeighted + sbqItr.getRate()) / 2;
											}
											quantityAccumulated += sbqItr.getQuantity();
										}
									}
									sbq.setRate(rateWeighted == null ? 0.0 : rateWeighted);
									sbq.setQuantity(quantityAccumulated);
									sbqsAfterAccumulationOfQuantitiesAndWeightedRate.add(sbq);
								}
								for (StructureBoqQuantityMapping sbq : sbqsAfterAccumulationOfQuantitiesAndWeightedRate) {
									Double workorderQuantity = 0.0;
									Double workorderAmount = 0.0;
									for (WorkorderBoqWorkQtyMapping obj : woBoqQtys) {
										if (obj.getBoq().getId().equals(sbq.getBoq().getId())
												&& obj.getDescription().trim().equalsIgnoreCase(sbq.getDescription())) {
											workorderQuantity += obj.getQuantity();
											workorderAmount += (obj.getRate() * obj.getQuantity());
										}
									}
									Double leftoverAmountBasedQuantity = 0.0;
									if (sbq.getRate().doubleValue() > 0.0) {
										leftoverAmountBasedQuantity = ((sbq.getQuantity().doubleValue()
												* sbq.getRate().doubleValue()) - workorderAmount)
												/ sbq.getRate().doubleValue();
									} else {
										leftoverAmountBasedQuantity = sbq.getQuantity().doubleValue()
												- workorderQuantity.doubleValue();
									}
									sbq.setQuantity(leftoverAmountBasedQuantity);
									if (sbq.getQuantity().doubleValue() < 0.0) {
										sbq.setQuantity(0.0);
									}
								}
							}
							Object result = setObject
									.renderChildParentSbqs(sbqsAfterAccumulationOfQuantitiesAndWeightedRate).getData();
							WorkorderStructureTypeBoqResponse structureBoq = new WorkorderStructureTypeBoqResponse(
									location.getStructureTypeId(), location.getStructureType().getName(),
									location.getStructureId(),
									location.getStructure() != null ? location.getStructure().getName() : null, result);
							structureBoqResponse.add(structureBoq);
						}

					}
					otherBoqData.put(1, structureBoqResponse);
				}
			}

			if (hasWoBoqs) {
				if (otherBoqData.get(0) != null) {
					List<ChainageBoqParentChildDTO> data = (List<ChainageBoqParentChildDTO>) otherBoqData.get(0);
					if (data != null) {
						Set<Long> existedBoqIds = new HashSet<>();
						for (WorkorderBoqWorkParentChildDTO woBoq : pcBoqs) {
							if (woBoq.getType().equals(ItemType.Boq.toString())) {
								existedBoqIds.add(woBoq.getId());
							}
							for (ChainageBoqParentChildDTO obj : data) {
								if (woBoq.getType().equals(ItemType.Boq.toString())
										&& obj.getType().equals(ItemType.Boq.toString())
										&& woBoq.getId().equals(obj.getId())) {
									woBoq.setClientRate(obj.getClientRate());
									woBoq.setThresholdRate(obj.getThresholdRate());
									woBoq.setAvailableQuantity(obj.getAvailableQuantity());
								}
							}
						}
						Set<Long> unsavedBoqIds = new HashSet<>();
						Set<ChainageBoqParentChildDTO> unsavedBoqs = new LinkedHashSet<ChainageBoqParentChildDTO>();
						for (ChainageBoqParentChildDTO obj : data) {
							if (!obj.getType().equals(ItemType.Boq.toString()))
								continue;
							boolean doesExist = false;
							for (Long id : existedBoqIds) {
								if (id.equals(obj.getId())) {
									doesExist = true;
									break;
								}
							}
							if (!doesExist) {
								unsavedBoqIds.add(obj.getId());
								unsavedBoqs.add(obj);
							}
						}
						int uniquePid = 0;
						int sortingIndex = 0;
						for (WorkorderBoqWorkParentChildDTO woBoq : pcBoqs) {
							if (uniquePid < woBoq.getPid().intValue())
								uniquePid = woBoq.getPid().intValue();
						}
						for (ChainageBoqParentChildDTO obj : unsavedBoqs) {
							BoqItem boq = null;
							for (BoqItem item : boqs) {
								if (item.getId().equals(obj.getId())) {
									boq = item;
									break;
								}
							}
							boolean catExists = false;
							boolean subcatExists = false;
							Integer catPid = null;
							Integer subcatPid = null;
							if (boq.getCategory() != null) {
								for (WorkorderBoqWorkParentChildDTO woBoq : pcBoqs) {
									if (woBoq.getType().equals(ItemType.Category.toString())
											&& woBoq.getId().equals(boq.getCategory().getId())) {
										catExists = true;
										catPid = woBoq.getPid();
										break;
									}
								}
								if (!catExists) {
									CategoryItem category = boq.getCategory();
									WorkorderBoqWorkParentChildDTO parentDTO = new WorkorderBoqWorkParentChildDTO(
											category.getId(), null, category.getName(), category.getName(), null, null,
											null, category.getCode(), category.getStandardBookCode(), null, null, null,
											null, null, null, null, ItemType.Category.toString(), null, ++uniquePid,
											null, null, null, ++sortingIndex);
									catPid = parentDTO.getPid();
									pcBoqs.add(parentDTO);
								}
							}
							if (boq.getSubcategory() != null) {
								for (WorkorderBoqWorkParentChildDTO woBoq : pcBoqs) {
									if (woBoq.getType().equals(ItemType.Subcategory.toString())
											&& woBoq.getId().equals(boq.getSubcategory().getId())) {
										subcatExists = true;
										subcatPid = woBoq.getPid();
										break;
									}
								}
								if (!subcatExists) {
									SubcategoryItem subcategory = boq.getSubcategory();
									WorkorderBoqWorkParentChildDTO subparentDTO = new WorkorderBoqWorkParentChildDTO(
											subcategory.getId(), null, subcategory.getName(), subcategory.getName(),
											null, null, null, subcategory.getCode(), subcategory.getStandardBookCode(),
											null, null, null, null, null, null, null, ItemType.Subcategory.toString(),
											ItemType.Category.toString(), ++uniquePid, catPid, obj.getRemark(), null,
											++sortingIndex);
									subcatPid = subparentDTO.getPid();
									pcBoqs.add(subparentDTO);
								}
							}
							WorkorderBoqWorkParentChildDTO childDTO = new WorkorderBoqWorkParentChildDTO(boq.getId(),
									null, boq.getName(), obj.getImportDescription(), obj.getImportDescription(),
									boq.getUnit().getId(), boq.getUnit().getName(), boq.getCode(),
									boq.getStandardBookCode(), obj.getClientRate(), obj.getThresholdRate(), 0.0,
									obj.getAvailableQuantity(), 0.0, 0.0, false, ItemType.Boq.toString(),
									boq.getSubcategory() != null ? ItemType.Subcategory.toString()
											: ItemType.Category.toString(),
									++uniquePid, boq.getSubcategory() != null ? subcatPid : catPid, obj.getRemark(),
									false, ++sortingIndex);
							pcBoqs.add(childDTO);
						}
					}
					renderDTO.setBoqs(pcBoqs);
				} else if (otherBoqData.get(1) != null) {
					List<WorkorderStructureTypeBoqResponse> dataList = (List<WorkorderStructureTypeBoqResponse>) otherBoqData
							.get(1);
					List<WorkorderStructureTypeBoqResponse> structurePsBoqs = new ArrayList<>();
					for (WorkorderStructureTypeBoqResponse dataObj : dataList) {
						WorkorderStructureTypeBoqResponse structureBoq = null;
						for (WorkorderStructureTypeBoqResponse structureBoqRes : (List<WorkorderStructureTypeBoqResponse>) renderDTO
								.getStructureBoqs()) {
							if (structureBoqRes.getStructureTypeId().equals(dataObj.getStructureTypeId())
									&& ((structureBoqRes.getStructureId() == null && dataObj.getStructureId() == null)
											|| (structureBoqRes.getStructureId() != null
													&& dataObj.getStructureId() != null && structureBoqRes
															.getStructureId().equals(dataObj.getStructureId())))) {
								structureBoq = structureBoqRes;
								break;
							}
						}
						List<SbqParentChildDTO> data = (List<SbqParentChildDTO>) dataObj.getBoqs();

						if (structureBoq == null
								|| (List<WorkorderBoqWorkParentChildDTO>) structureBoq.getBoqs() == null) {
							continue;
						}
						List<WorkorderBoqWorkParentChildDTO> psBoqs = (List<WorkorderBoqWorkParentChildDTO>) structureBoq
								.getBoqs();
						if (psBoqs == null) {
							psBoqs = new ArrayList<>();
						}
						if (data != null) {
							Set<Long> existedBoqIds = new HashSet<>();
							for (WorkorderBoqWorkParentChildDTO woBoq : psBoqs) {
								if (woBoq.getType().equals(ItemType.Boq.toString())) {
									existedBoqIds.add(woBoq.getId());
								}
								for (SbqParentChildDTO obj : data) {
									if (woBoq.getType().equals(ItemType.Boq.toString())
											&& obj.getType().equals(ItemType.Boq.toString())
											&& woBoq.getId().equals(obj.getId())) {
										woBoq.setClientRate(obj.getClientRate());
										woBoq.setThresholdRate(obj.getThresholdRate());
										woBoq.setAvailableQuantity(obj.getAvailableQuantity());
									}
								}
							}
							Set<Long> unsavedBoqIds = new HashSet<>();
							Set<SbqParentChildDTO> unsavedBoqs = new LinkedHashSet<SbqParentChildDTO>();
							for (SbqParentChildDTO obj : data) {
								if (!obj.getType().equals(ItemType.Boq.toString()))
									continue;
								boolean doesExist = false;
								for (Long id : existedBoqIds) {
									if (id.equals(obj.getId())) {
										doesExist = true;
										break;
									}
								}
								if (!doesExist) {
									unsavedBoqIds.add(obj.getId());
									unsavedBoqs.add(obj);
								}
							}
							int uniquePid = 0;
							int sortingIndex = 0;
							for (WorkorderBoqWorkParentChildDTO woBoq : psBoqs) {
								if (uniquePid < woBoq.getPid().intValue())
									uniquePid = woBoq.getPid().intValue();
							}
							for (SbqParentChildDTO obj : unsavedBoqs) {
								BoqItem boq = null;
								for (BoqItem item : boqs) {
									if (item.getId().equals(obj.getId())) {
										boq = item;
										break;
									}
								}
								boolean catExists = false;
								boolean subcatExists = false;
								Integer catPid = null;
								Integer subcatPid = null;
								if (boq.getCategory() != null) {
									for (WorkorderBoqWorkParentChildDTO woBoq : psBoqs) {
										if (woBoq.getType().equals(ItemType.Category.toString())
												&& woBoq.getId().equals(boq.getCategory().getId())) {
											catExists = true;
											catPid = woBoq.getPid();
											break;
										}
									}
									if (!catExists) {
										CategoryItem category = boq.getCategory();
										WorkorderBoqWorkParentChildDTO parentDTO = new WorkorderBoqWorkParentChildDTO(
												category.getId(), null, category.getName(), category.getName(), null,
												null, null, category.getCode(), category.getStandardBookCode(), null,
												null, null, null, null, null, null, ItemType.Category.toString(), null,
												++uniquePid, null, null, null, ++sortingIndex);
										catPid = parentDTO.getPid();
										psBoqs.add(parentDTO);
									}
								}
								if (boq.getSubcategory() != null) {
									for (WorkorderBoqWorkParentChildDTO woBoq : psBoqs) {
										if (woBoq.getType().equals(ItemType.Subcategory.toString())
												&& woBoq.getId().equals(boq.getSubcategory().getId())) {
											subcatExists = true;
											subcatPid = woBoq.getPid();
											break;
										}
									}
									if (!subcatExists) {
										SubcategoryItem subcategory = boq.getSubcategory();
										WorkorderBoqWorkParentChildDTO subparentDTO = new WorkorderBoqWorkParentChildDTO(
												subcategory.getId(), null, subcategory.getName(), subcategory.getName(),
												null, null, null, subcategory.getCode(),
												subcategory.getStandardBookCode(), null, null, null, null, null, null,
												null, ItemType.Subcategory.toString(), ItemType.Category.toString(),
												++uniquePid, catPid, null, null, ++sortingIndex);
										subcatPid = subparentDTO.getPid();
										psBoqs.add(subparentDTO);
									}
								}
								WorkorderBoqWorkParentChildDTO childDTO = new WorkorderBoqWorkParentChildDTO(
										boq.getId(), null, boq.getName(), obj.getImportDescription(),
										obj.getImportDescription(), obj.getUnitId(), obj.getUnitName(), boq.getCode(),
										boq.getStandardBookCode(), obj.getClientRate(), obj.getThresholdRate(), 0.0,
										obj.getAvailableQuantity(), 0.0, 0.0, false, ItemType.Boq.toString(),
										boq.getSubcategory() != null ? ItemType.Subcategory.toString()
												: ItemType.Category.toString(),
										++uniquePid, boq.getSubcategory() != null ? subcatPid : catPid, obj.getRemark(),
										false, ++sortingIndex);
								psBoqs.add(childDTO);
							}
						}
						boolean hasBoqSelected = false;
						for (WorkorderBoqWorkParentChildDTO psBoq : psBoqs) {
							if (psBoq.getAlreadySaved() != null && psBoq.getAlreadySaved()) {
								hasBoqSelected = true;
								break;
							}
						}
						if (hasBoqSelected) {
							structurePsBoqs.add(new WorkorderStructureTypeBoqResponse(dataObj.getStructureTypeId(),
									dataObj.getStructureTypeName(), dataObj.getStructureId(),
									dataObj.getStructureName(), psBoqs));
						}
					}
					renderDTO.setStructureBoqs(structurePsBoqs);
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), renderDTO, Responses.SUCCESS.toString());
		} catch (

		Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	public CustomResponse versioningWorkorderBoqWorkFlow(AmendWorkorderInvocationUpdateStateRequest clientRequestDTO,
			Long workorderId) {

		try {
			Workorder workorder = workorderDao.fetchWorkorderById(workorderId);
			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");

			WorkorderBoqWork boqWork = woBoqWorkDao.fetchWorkorderBoqWorkByWorkorderId(workorderId);
			if (boqWork == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid labourWorkId.");

			WorkorderVersion workorderVersion = setObject.workorderEntityToVersionEntity(workorder);
			workorderVersion.setCreatedBy(clientRequestDTO.getUserDetail().getId());
			Long workorderVersionId = workorderDao.saveWorkorderVersion(workorderVersion);

			if (workorderVersionId == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderVersionId.");
			}

			WorkorderBoqWorkVersion boqWorkVersion = setObject.workorderBoqWorkEntityToVersionEntity(boqWork);
			boqWorkVersion.setWorkorderVersionId(workorderVersionId);
			boqWorkVersion.setCreatedBy(clientRequestDTO.getUserDetail().getId());

			Long boqWorkVersionId = woBoqWorkDao.saveWorkorderBoqWorkVersion(boqWorkVersion);

			if (boqWorkVersionId != null && boqWorkVersionId > 0L) {

				if (boqWork.getBoqWorkQty() != null && !boqWork.getBoqWorkQty().isEmpty()) {

					for (WorkorderBoqWorkQtyMapping boqWorkQtyMapping : boqWork.getBoqWorkQty()) {

						WorkorderBoqWorkQtyMappingVersion boqWorkQtyMappingVersionObj = setObject
								.workorderBoqWorkQtyMappingEntityToVersionEntity(boqWorkQtyMapping);
						boqWorkQtyMappingVersionObj.setBoqWorkVersionId(boqWorkVersionId);
						boqWorkQtyMappingVersionObj.setCreatedBy(clientRequestDTO.getUserDetail().getId());

						woBoqWorkDao.saveWorkorderBoqWorkQtyMapVersion(boqWorkQtyMappingVersionObj);

					}

				}

				if (boqWork.getLocations() != null && !boqWork.getLocations().isEmpty()) {

					for (WorkorderBoqWorkLocation boqWorkLocation : boqWork.getLocations()) {

						WorkorderBoqWorkLocationVersion boqWorkLocationVersionObj = setObject
								.workorderBoqWorkLocationEntityToVersionEntity(boqWorkLocation);
						boqWorkLocationVersionObj.setBoqWorkVersionId(boqWorkVersionId);
						boqWorkLocationVersionObj.setCreatedBy(clientRequestDTO.getUserDetail().getId());

						woBoqWorkDao.saveWorkorderBoqWorkLocationVersion(boqWorkLocationVersionObj);

					}

				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), workorderVersionId, "Added.");

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse amendWorkorderBoqWorkFlow(UserDetail userDetail, Long amendWorkorderId, Long workorderId) {

		try {

			WorkorderBoqWork boqWork = woBoqWorkDao.fetchWorkorderBoqWorkByWorkorderId(workorderId);
			if (boqWork == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid labourWorkId.");

			WorkorderBoqWork amendBoqWork = setObject.workorderBoqWorkEntityToAmendEntity(boqWork);
			amendBoqWork.setWorkorderId(amendWorkorderId);
			amendBoqWork.setModifiedBy(userDetail.getId());

			Long amendBoqWorkId = woBoqWorkDao.saveWorkorderBoqWork(amendBoqWork);

			if (amendBoqWorkId != null && amendBoqWorkId > 0L) {

				if (boqWork.getBoqWorkQty() != null && !boqWork.getBoqWorkQty().isEmpty()) {

					for (WorkorderBoqWorkQtyMapping boqWorkQtyMapping : boqWork.getBoqWorkQty()) {

						WorkorderBoqWorkQtyMapping amendBoqWorkQtyMappingObj = setObject
								.workorderBoqWorkQtyMappingEntityToAmendEntity(boqWorkQtyMapping);
						amendBoqWorkQtyMappingObj.setBoqWork(amendBoqWork);
						amendBoqWorkQtyMappingObj.setModifiedBy(userDetail.getId());

						woBoqWorkDao.saveWorkorderBoqWorkQtyMap(amendBoqWorkQtyMappingObj);

					}

				}

				if (boqWork.getLocations() != null && !boqWork.getLocations().isEmpty()) {

					for (WorkorderBoqWorkLocation boqWorkLocation : boqWork.getLocations()) {

						WorkorderBoqWorkLocation amendBoqWorkLocationObj = setObject
								.workorderBoqWorkLocationEntityToAmendEntity(boqWorkLocation);
						amendBoqWorkLocationObj.setBoqWork(amendBoqWork);
						amendBoqWorkLocationObj.setModifiedBy(userDetail.getId());

						woBoqWorkDao.saveWorkorderBoqWorkLocation(amendBoqWorkLocationObj);

					}

				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), amendBoqWorkId, Responses.SUCCESS.name());

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
