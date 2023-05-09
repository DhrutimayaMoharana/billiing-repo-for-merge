package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import erp.boq_mgmt.dao.BorewellBoqMappingDao;
import erp.boq_mgmt.dao.ChainageBorewellBoqQuantityDao;
import erp.boq_mgmt.dao.ChainageDao;
import erp.boq_mgmt.dao.WorkorderBoqWorkDao;
import erp.boq_mgmt.dto.ChainageGenericBoqQuantityMappingDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.GenericBoqChainageRenderDTO;
import erp.boq_mgmt.dto.GenericBoqMappingDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.Chainage;
import erp.boq_mgmt.entity.GenericBoqMappingHighway;
import erp.boq_mgmt.entity.GenericChainageBoqQuantityMapping;
import erp.boq_mgmt.entity.WorkorderBoqWorkQtyMapping;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.UnitTypes;
import erp.boq_mgmt.service.ChainageBorewellBoqQuantityService;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class ChainageBorewellBoqQuantityServiceImpl implements ChainageBorewellBoqQuantityService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ChainageBorewellBoqQuantityDao cbqDao;
	@Autowired
	private SetObject setObject;
	@Autowired
	private ChainageDao chainageDao;
//	@Autowired
//	private ChainageService chainageService;
	@Autowired
	private BorewellBoqMappingDao bbmDao;
	@Autowired
	private WorkorderBoqWorkDao woBoqWorkDao;

	@Override
	public CustomResponse addChainageBoqQuantityMapping(ChainageGenericBoqQuantityMappingDTO cbqMapDTO) {
		try {
			GenericChainageBoqQuantityMapping cbqObj = setObject.chainageBoqQtyMapDtoToEntity(cbqMapDTO);
			cbqObj.setModifiedOn(new Date());
			Long id = cbqDao.saveChainageGenericBoqQuantityMapping(cbqObj);
//			if (id != null && id.longValue() > 0L) {
//				ChainageBorewellBoqQuantityTransacs cbqTransac = setObject.cbqEntityMappingToTransac(cbqObj);
//				cbqTransac.setCreatedOn(new Date());
//				cbqDao.saveChainageBorewellBoqQuantityTransac(cbqTransac);
//			}
			return new CustomResponse(Responses.SUCCESS.getCode(),
					((id != null && id.longValue() > 0L) ? id : "Already exists..."), Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateChainageBoqQuantityMapping(ChainageGenericBoqQuantityMappingDTO cbqMapDTO) {
		try {
			GenericChainageBoqQuantityMapping dbObj = cbqDao.fetchCbqByIdV1(cbqMapDTO.getId(),
					cbqMapDTO.getGenericBoq().getWorkorderTypeId());
			if (dbObj == null)
				return new CustomResponse(Responses.SUCCESS.getCode(), "Does not exist...",
						Responses.SUCCESS.toString());
			GenericChainageBoqQuantityMapping cbqObj = setObject.updatedCbq(dbObj,
					setObject.chainageBoqQtyMapDtoToEntity(cbqMapDTO));
			Boolean isSaved = cbqDao.updateChainageGenericBoqQuantityMapping(cbqObj);
			if (!(dbObj.getSiteId().equals(cbqObj.getSiteId()) && dbObj.getBoq().getId().equals(cbqObj.getBoq().getId())
					&& dbObj.getIsActive().equals(cbqObj.getIsActive())
					&& dbObj.getChainage().getId().equals(cbqObj.getChainage().getId())
					&& dbObj.getLhsQuantity().equals(cbqObj.getLhsQuantity())
					&& dbObj.getRhsQuantity().equals(cbqObj.getRhsQuantity()))) {
//				ChainageBorewellBoqQuantityTransacs cbqTransac = setObject.cbqEntityMappingToTransac(cbqObj);
//				cbqTransac.setCreatedOn(new Date());
//				cbqDao.saveChainageBorewellBoqQuantityTransac(cbqTransac);
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), isSaved, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse importChainageBoqQuantityMappingExcel(MultipartFile excelFile, SearchDTO search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomResponse getChainageBoqQuantities(SearchDTO search) {
		try {
			List<GenericChainageBoqQuantityMapping> cbqList = cbqDao
					.fetchChainageGenericBoqQuantityMappingBySearch(search);
			List<ChainageGenericBoqQuantityMappingDTO> result = new ArrayList<ChainageGenericBoqQuantityMappingDTO>();
			search.setSearchField(null);
			List<Chainage> chainages = chainageDao.fetchChainages(search);
			if (cbqList != null) {
				for (GenericChainageBoqQuantityMapping cbq : cbqList) {
					result.add(setObject.chainageBoqQtyMapEntityToDto(cbq));
				}
			}
			if (search.getFromChainageId() != null && search.getToChainageId() != null) {
				String fromChainage = null;
				String toChainage = null;
				for (Chainage chainage : chainages) {
					if (chainage.getId().equals(search.getFromChainageId()))
						fromChainage = chainage.getName();
					if (chainage.getId().equals(search.getToChainageId()))
						toChainage = chainage.getName();
				}
				if (fromChainage != null && toChainage != null) {
					String fromChainageNum = fromChainage.replaceAll("[^0-9]", "");
					String toChainageNum = toChainage.replaceAll("[^0-9]", "");
					if (!fromChainageNum.isEmpty() && !toChainageNum.isEmpty()) {
						for (int i = 0; i < result.size(); i++) {
							ChainageGenericBoqQuantityMappingDTO cbqDto = result.get(i);
							if (cbqDto.getChainage() != null && cbqDto.getChainage().getName() != null) {
								String chainageNum = cbqDto.getChainage().getName().replaceAll("[^0-9]", "");
								if (!chainageNum.isEmpty()) {
									int chainageInt = Integer.parseInt(chainageNum);
									int fromChainageInt = Integer.parseInt(fromChainageNum);
									int toChainageInt = Integer.parseInt(toChainageNum);
									if (!(chainageInt >= fromChainageInt && chainageInt <= toChainageInt)) {
										result.set(i, null);
									}
								}
							} else {
								result.set(i, null);
							}
						}
						result.removeAll(Collections.singleton(null));
					}

				}
			}
			if (result != null && result.size() > 0) {
				result.get(0).setLength(0.0);
				result.get(0).setVolume(0.0);
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

//	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public CustomResponse getBoqWiseCbq(SearchDTO search) {
		try {
			List<GenericChainageBoqQuantityMapping> cbqList = cbqDao.fetchBoqWiseCbqV1(search);
			List<ChainageGenericBoqQuantityMappingDTO> result = new ArrayList<ChainageGenericBoqQuantityMappingDTO>();
			double totalDistributedQuantity = 0.0;
			if (cbqList != null) {
				int counter = 0;
				for (GenericChainageBoqQuantityMapping cbq : cbqList) {
					cbq.setBoq(null);
					cbq.setGenericBoq(null);
					totalDistributedQuantity += ((cbq.getRhsQuantity() != null ? cbq.getRhsQuantity() : 0.0)
							+ (cbq.getLhsQuantity() != null ? cbq.getLhsQuantity() : 0.0));
					ChainageGenericBoqQuantityMappingDTO obj = setObject.chainageBoqQtyMapEntityToDto(cbq);
					obj.setArea((obj.getLhsQuantity() != null ? obj.getLhsQuantity() : 0.0)
							+ (obj.getRhsQuantity() != null ? obj.getRhsQuantity() : 0.0));
					if (obj.getChainage() != null && obj.getChainage().getPrevious() != null) {
						Integer length = ((Integer.parseInt(obj.getChainage().getName().replaceAll("[^0-9]", "")))
								- (Integer
										.parseInt(obj.getChainage().getPrevious().getName().replaceAll("[^0-9]", ""))));
						obj.setLength(length.doubleValue());
						if (counter == 0) {
							obj.setLength(0.0);
							obj.setVolume(0.0);
						}
						if (counter > 0) {
							Double previousArea = (cbqList.get(counter - 1).getLhsQuantity() != null
									? cbqList.get(counter - 1).getLhsQuantity()
									: 0.0)
									+ (cbqList.get(counter - 1).getRhsQuantity() != null
											? cbqList.get(counter - 1).getRhsQuantity()
											: 0.0);
							Double currentArea = obj.getArea();
							double meanArea = (previousArea + currentArea) / 2;
							if (cbq.getStructureRemark() == null || cbq.getStructureRemark().isBlank()) {
								obj.setVolume(meanArea * obj.getLength());
							} else {
								obj.setVolume(0.0);
							}
						}
					} else {
						obj.setLength(0.0);
						obj.setVolume(0.0);
					}
					result.add(obj);
					counter++;
				}
			}
			GenericBoqMappingHighway hbm = bbmDao.fetchGenricCategoryBoqById(search.getGenericBoqId(),
					search.getWorkOrderTypeId());
			GenericBoqMappingDTO hbmToReturn = setObject.boqCategoryMapEntityToDto(hbm);
			GenericBoqChainageRenderDTO renderResult = new GenericBoqChainageRenderDTO(hbmToReturn, result,
					totalDistributedQuantity);
			return new CustomResponse(Responses.SUCCESS.getCode(), renderResult, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getCbqById(SearchDTO search) {
		try {
			GenericChainageBoqQuantityMapping cbq = cbqDao.fetchCbqByIdV1(search.getCbqId(),
					search.getWorkOrderTypeId());
			ChainageGenericBoqQuantityMappingDTO result = setObject.chainageBoqQtyMapEntityToDto(cbq);
			result.setArea((result.getLhsQuantity() != null ? result.getLhsQuantity() : 0.0)
					+ (result.getRhsQuantity() != null ? result.getRhsQuantity() : 0.0));
			if (result.getChainage() != null && result.getChainage().getPrevious() != null) {
				result.setLength((Double.parseDouble(result.getChainage().getName().replaceAll("[^0-9]", "")))
						- (Double.parseDouble(result.getChainage().getPrevious().getName().replaceAll("[^0-9]", ""))));
				result.setVolume(result.getArea() * result.getLength());
			} else {
				result.setLength(0.0);
				result.setVolume(0.0);
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getChainageQuantitiesPerBoq(SearchDTO search) {
		try {
			List<GenericChainageBoqQuantityMapping> cbqList = cbqDao
					.fetchChainageGenericBoqQuantityMappingBySearch(search);
			List<ChainageGenericBoqQuantityMappingDTO> result = new ArrayList<ChainageGenericBoqQuantityMappingDTO>();
			Set<Long> boqIds = new HashSet<>();
			search.setSearchField(null);
			List<Chainage> chainages = chainageDao.fetchChainages(search);
			if (cbqList != null) {
				for (GenericChainageBoqQuantityMapping cbq : cbqList) {
					result.add(setObject.chainageBoqQtyMapEntityToDto(cbq));
				}
			}
			if (search.getFromChainageId() != null && search.getToChainageId() != null) {
				String fromChainage = null;
				String toChainage = null;
				for (Chainage chainage : chainages) {
					if (chainage.getId().equals(search.getFromChainageId()))
						fromChainage = chainage.getName();
					if (chainage.getId().equals(search.getToChainageId()))
						toChainage = chainage.getName();
				}
				if (fromChainage != null && toChainage != null) {
					String fromChainageNum = fromChainage.replaceAll("[^0-9]", "");
					String toChainageNum = toChainage.replaceAll("[^0-9]", "");
					if (!fromChainageNum.isEmpty() && !toChainageNum.isEmpty()) {
						for (int i = 0; i < result.size(); i++) {
							ChainageGenericBoqQuantityMappingDTO cbqDto = result.get(i);
							if (cbqDto.getChainage() != null && cbqDto.getChainage().getName() != null) {
								String chainageNum = cbqDto.getChainage().getName().replaceAll("[^0-9]", "");
								if (!chainageNum.isEmpty()) {
									int chainageInt = Integer.parseInt(chainageNum);
									int fromChainageInt = Integer.parseInt(fromChainageNum);
									int toChainageInt = Integer.parseInt(toChainageNum);
									if (!(chainageInt >= fromChainageInt && chainageInt <= toChainageInt)) {
										result.set(i, null);
									}
								}
							} else {
								result.set(i, null);
							}
						}
						result.removeAll(Collections.singleton(null));
					}

				}
			}
			List<ChainageGenericBoqQuantityMappingDTO> boqWiseResult = new ArrayList<ChainageGenericBoqQuantityMappingDTO>();
			if (result != null && result.size() > 0) {
				for (ChainageGenericBoqQuantityMappingDTO obj : result) {
					boqIds.add(obj.getBoq().getId());
				}
				if (boqIds.size() > 0) {
					for (Long boqId : boqIds) {
						ChainageGenericBoqQuantityMappingDTO obj = null;
						for (ChainageGenericBoqQuantityMappingDTO objDTO : result) {
							if (boqId.equals(objDTO.getBoq().getId())) {
								if (obj == null) {
									obj = new ChainageGenericBoqQuantityMappingDTO(null, null, objDTO.getBoq(),
											objDTO.getGenericBoq(), objDTO.getSiteId(), objDTO.getLhsQuantity(),
											objDTO.getRhsQuantity(), objDTO.getStructureRemark(), true, null, null,
											objDTO.getCompanyId());
								} else {
									Double lhsQuant = obj.getLhsQuantity() != null ? obj.getLhsQuantity() : 0.0;
									Double rhsQuant = obj.getRhsQuantity() != null ? obj.getRhsQuantity() : 0.0;
									obj.setLhsQuantity(lhsQuant);
									obj.setRhsQuantity(rhsQuant);
									obj.setArea(lhsQuant + rhsQuant);
									Double lengthFromPrevious = 0.00;
									if (obj.getChainage() != null && obj.getChainage().getPrevious() != null) {
										if (obj.getChainage().getName() != null
												&& obj.getChainage().getPrevious().getName() != null) {
											Double previousChainageNum = Double.parseDouble(
													obj.getChainage().getPrevious().getName().replaceAll("[^0-9]", ""));
											Double currentChainageNum = Double
													.parseDouble(obj.getChainage().getName().replaceAll("[^0-9]", ""));
											lengthFromPrevious = currentChainageNum - previousChainageNum;
										}
									}
									obj.setLength(lengthFromPrevious);
									obj.setVolume(obj.getArea() * obj.getLength());
								}
							}
						}
						if (obj != null)
							boqWiseResult.add(obj);
					}
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), boqWiseResult, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse removeChainageBoqQuantityMapping(SearchDTO search) {
		try {
			GenericChainageBoqQuantityMapping cbq = cbqDao.fetchCbqByIdV1(search.getCbqId(),
					search.getWorkOrderTypeId());
			GenericBoqMappingHighway hbm = cbq.getGenericBoq();
			List<GenericChainageBoqQuantityMapping> oldCbqs = cbqDao
					.fetchChainageGenericBoqQuantitiesByHbmIdWithOneCbqLess(cbq.getGenericBoq().getId(), cbq.getId());
			Double totalArea = 0.0;
			Double totalVolume = 0.0;
			for (int i = 0; i < oldCbqs.size(); i++) {
				GenericChainageBoqQuantityMapping toSaveObj = oldCbqs.get(i);
				totalArea += (toSaveObj.getLhsQuantity() + toSaveObj.getRhsQuantity());
				if (i == 0) {
					continue;
				}
				GenericChainageBoqQuantityMapping previousToSaveObj = oldCbqs.get(i - 1);
				String numericChainage = toSaveObj.getChainage().getName().replaceAll("[^0-9]", "");
				String previousChainage = previousToSaveObj.getChainage().getName().replaceAll("[^0-9]", "");
				Double length = Double.valueOf(numericChainage) - Double.valueOf(previousChainage);
				Double previousArea = previousToSaveObj.getLhsQuantity() + previousToSaveObj.getRhsQuantity();
				Double currentArea = toSaveObj.getLhsQuantity() + toSaveObj.getRhsQuantity();
				if (!(toSaveObj.getStructureRemark() == null || toSaveObj.getStructureRemark().isBlank())) {
					length = 0.0;
				}
				totalVolume += ((currentArea + previousArea) / 2) * length;
			}
			Double totalBoqQuantity = totalArea;
			if (cbq.getBoq().getUnit().getType().getId().equals(UnitTypes.VOLUME.getTypeId())) {
				totalBoqQuantity = totalVolume;
			}
			search.setBoqId(cbq.getBoq().getId());
			List<WorkorderBoqWorkQtyMapping> woBoqWorkQtys = woBoqWorkDao.fetchWoBoqWorkQtysByBoqId(search);
			Double woBoqQty = 0.0;
			if (woBoqWorkQtys != null) {
				for (WorkorderBoqWorkQtyMapping woBoq : woBoqWorkQtys) {
					woBoqQty += woBoq.getQuantity();
				}
			}
			if (totalBoqQuantity < woBoqQty) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Quantity used in workorder, can't remove!");
			}
			cbq.setIsActive(false);
			cbq.setModifiedBy(search.getUserId());
			cbq.setModifiedOn(new Date());

			Boolean update = cbqDao.updateChainageGenericBoqQuantityMapping(cbq);
			if (update) {
				if (hbm.getQuantity().doubleValue() != totalBoqQuantity) {
					hbm.setQuantity(totalBoqQuantity);
					hbm.setVersion(hbm.getVersion() + 1);
					hbm.setQuantityVersion(hbm.getQuantityVersion() + 1);
				}
				bbmDao.updateCategoryBoqMapping(hbm);
//				BorewellBoqTransac bcmTransac = setObject.BorewellBoqEntityMappingToTransac(hbm);
//				bcmTransac.setCreatedOn(new Date());
//				bbmDao.saveBoqCategoryTransac(bcmTransac);

//				ChainageBorewellBoqQuantityTransacs cbqTransac = setObject.cbqEntityMappingToTransac(cbq);
//				cbqTransac.setCreatedOn(new Date());
//				cbqDao.saveChainageBorewellBoqQuantityTransac(cbqTransac);
				return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());
			}

			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), false, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
