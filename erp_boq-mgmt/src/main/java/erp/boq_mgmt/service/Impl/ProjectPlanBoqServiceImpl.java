package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.boq_mgmt.dao.ProjectPlanBoqDao;
import erp.boq_mgmt.dao.SiteDao;
import erp.boq_mgmt.dao.StructureBoqQuantityDao;
import erp.boq_mgmt.dao.Impl.HighwayBoqMapDaoImpl;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.ProjectPlanBoqAddUpdateRequest;
import erp.boq_mgmt.dto.request.ProjectPlanBoqDistributionAddUpdateRequest;
import erp.boq_mgmt.dto.request.ProjectPlanBoqFetchRequest;
import erp.boq_mgmt.dto.response.IdNameDTO;
import erp.boq_mgmt.dto.response.ProjectPlanBoqResponse;
import erp.boq_mgmt.dto.response.StructureTypeWithStructuresFetchResponse;
import erp.boq_mgmt.entity.HighwayBoqMapping;
import erp.boq_mgmt.entity.ProjectPlanBoq;
import erp.boq_mgmt.entity.ProjectPlanBoqDistribution;
import erp.boq_mgmt.entity.Site;
import erp.boq_mgmt.entity.StructureBoqQuantityMapping;
import erp.boq_mgmt.entity.StructureType;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.WorkType;
import erp.boq_mgmt.service.ProjectPlanBoqService;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class ProjectPlanBoqServiceImpl implements ProjectPlanBoqService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private ProjectPlanBoqDao projectPlanBoqDao;

	@Autowired
	private HighwayBoqMapDaoImpl highwayBoqMapDaoImpl;

	@Autowired
	private StructureBoqQuantityDao structureBoqQuantityDao;

	@Autowired
	private SiteDao siteDao;

	@Override
	public CustomResponse addUpdateProjectPlanBoq(ProjectPlanBoqAddUpdateRequest requestDTO) {
		try {

			if (requestDTO.getWorkType().equals(WorkType.HIGHWAY)) {
				ProjectPlanBoq ppbDbObj = projectPlanBoqDao.fetchProjectPlanBoqByWorkTypeAndBoqId(
						requestDTO.getWorkType(), requestDTO.getBoqId(), requestDTO.getStructureTypeId(),
						requestDTO.getStructureId());

				ProjectPlanBoq projectPlanBoq = ppbDbObj;

				if (projectPlanBoq == null) {

					projectPlanBoq = new ProjectPlanBoq(null, requestDTO.getSiteId(), requestDTO.getWorkType(),
							requestDTO.getBoqId(), null, null, true, new Date(),
							requestDTO.getUserDetail().getId().intValue(), new Date(),
							requestDTO.getUserDetail().getId().intValue());

					Long projectPlanBoqId = projectPlanBoqDao.saveProjectPlanBoq(projectPlanBoq);
					projectPlanBoq.setId(projectPlanBoqId);

				}

				List<ProjectPlanBoqDistribution> ppbDistributionList = projectPlanBoqDao
						.fetchProjectPlanBoqDistributionByProjectPlanBoqId(projectPlanBoq.getId());

				List<ProjectPlanBoqDistribution> distributionListToSave = new ArrayList<ProjectPlanBoqDistribution>();
				List<ProjectPlanBoqDistribution> distributionListToUpdate = new ArrayList<ProjectPlanBoqDistribution>();
				Double totalDistributedQuantity = 0.0;

				if (ppbDistributionList != null && !ppbDistributionList.isEmpty())
					for (ProjectPlanBoqDistribution dbObj : ppbDistributionList) {

						for (ProjectPlanBoqDistributionAddUpdateRequest reqObj : requestDTO.getDistributionList()) {

							if (dbObj.getMonth().equals(reqObj.getMonth())
									&& dbObj.getYear().equals(reqObj.getYear())) {

								if (dbObj.getQuantityDistributed().equals(reqObj.getQuantityDistributed())) {
									totalDistributedQuantity = totalDistributedQuantity
											+ dbObj.getQuantityDistributed();
									break;
								} else {
									totalDistributedQuantity = totalDistributedQuantity
											+ reqObj.getQuantityDistributed();
									distributionListToUpdate
											.add(new ProjectPlanBoqDistribution(dbObj.getId(), projectPlanBoq.getId(),
													dbObj.getYear(), dbObj.getMonth(), reqObj.getQuantityDistributed(),
													new Date(), requestDTO.getUserDetail().getId().intValue()));
									break;
								}

							}

						}

					}

				for (ProjectPlanBoqDistributionAddUpdateRequest reqObj : requestDTO.getDistributionList()) {

					Boolean hasFound = false;
					if (ppbDistributionList != null && !ppbDistributionList.isEmpty())
						for (ProjectPlanBoqDistribution dbObj : ppbDistributionList) {
							if (dbObj.getMonth().equals(reqObj.getMonth())
									&& dbObj.getYear().equals(reqObj.getYear())) {
								hasFound = true;
								break;

							}
						}
					if (!hasFound) {
						totalDistributedQuantity = totalDistributedQuantity + reqObj.getQuantityDistributed();
						distributionListToSave.add(new ProjectPlanBoqDistribution(null, projectPlanBoq.getId(),
								reqObj.getYear(), reqObj.getMonth(), reqObj.getQuantityDistributed(), new Date(),
								requestDTO.getUserDetail().getId().intValue()));
					}

				}

				List<HighwayBoqMapping> highwayBoqMappingList = highwayBoqMapDaoImpl
						.fetchByBoqAndSite(requestDTO.getBoqId(), requestDTO.getSiteId().longValue());

				Double highwayBoqQuantity = 0.0;

				if (highwayBoqMappingList != null)
					for (HighwayBoqMapping highwayBoqMapping : highwayBoqMappingList) {
						highwayBoqQuantity = highwayBoqQuantity + highwayBoqMapping.getQuantity();
					}

				if (totalDistributedQuantity > highwayBoqQuantity) {
					return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null,
							"Distributed quantity is greater than total quantity.");
				}
				for (ProjectPlanBoqDistribution projectPlanBoqDistribution : distributionListToSave) {

					projectPlanBoqDao.saveProjectPlantBoqDistribution(projectPlanBoqDistribution);
				}
				for (ProjectPlanBoqDistribution projectPlanBoqDistribution : distributionListToUpdate) {

					projectPlanBoqDao.updateProjectPlantBoqDistribution(projectPlanBoqDistribution);
				}
				return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());
			}

			if (requestDTO.getWorkType().equals(WorkType.STRUCTURE)) {

				List<StructureBoqQuantityMapping> structureBoqMappingList = structureBoqQuantityDao
						.fetchStructureBoqMappingByBoqIdStructureIdAndTypeId(requestDTO.getBoqId(),
								requestDTO.getStructureId(), null, requestDTO.getSiteId().longValue());
				if (structureBoqMappingList == null || structureBoqMappingList.isEmpty()) {

					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Structure BoQ mapping not found.");
				}

				StructureBoqQuantityMapping structureBoqMapping = structureBoqMappingList.get(0);
				requestDTO.setStructureTypeId(structureBoqMapping.getStructure().getType().getId());

				ProjectPlanBoq ppbStructureDbObj = projectPlanBoqDao.fetchProjectPlanBoqByWorkTypeAndBoqId(
						requestDTO.getWorkType(), requestDTO.getBoqId(), null, requestDTO.getStructureId());

				// Structure Project Plan
				ProjectPlanBoq ppbStructure = ppbStructureDbObj;

				if (ppbStructure == null) {

					ppbStructure = new ProjectPlanBoq(null, requestDTO.getSiteId(), requestDTO.getWorkType(),
							requestDTO.getBoqId(), null, requestDTO.getStructureId(), true, new Date(),
							requestDTO.getUserDetail().getId().intValue(), new Date(),
							requestDTO.getUserDetail().getId().intValue());

					Long ppbStructureId = projectPlanBoqDao.saveProjectPlanBoq(ppbStructure);
					ppbStructure.setId(ppbStructureId);

				}

				// Structure Type Project Plan
				ProjectPlanBoq ppbStructureTypeDbObj = projectPlanBoqDao.fetchProjectPlanBoqByWorkTypeAndBoqId(
						requestDTO.getWorkType(), requestDTO.getBoqId(), requestDTO.getStructureTypeId(), null);

				ProjectPlanBoq ppbStructureType = ppbStructureTypeDbObj;

				if (ppbStructureType == null) {

					ppbStructureType = new ProjectPlanBoq(null, requestDTO.getSiteId(), requestDTO.getWorkType(),
							requestDTO.getBoqId(), requestDTO.getStructureTypeId(), null, true, new Date(),
							requestDTO.getUserDetail().getId().intValue(), new Date(),
							requestDTO.getUserDetail().getId().intValue());

					Long ppbStructureTypeId = projectPlanBoqDao.saveProjectPlanBoq(ppbStructureType);
					ppbStructureType.setId(ppbStructureTypeId);

				}

				// BoQ Project Plan
				ProjectPlanBoq ppbBoQDbObj = projectPlanBoqDao.fetchProjectPlanBoqByWorkTypeAndBoqId(
						requestDTO.getWorkType(), requestDTO.getBoqId(), null, null);

				ProjectPlanBoq ppbBoQ = ppbBoQDbObj;

				if (ppbBoQ == null) {

					ppbBoQ = new ProjectPlanBoq(null, requestDTO.getSiteId(), requestDTO.getWorkType(),
							requestDTO.getBoqId(), null, null, true, new Date(),
							requestDTO.getUserDetail().getId().intValue(), new Date(),
							requestDTO.getUserDetail().getId().intValue());

					Long ppbBoQId = projectPlanBoqDao.saveProjectPlanBoq(ppbBoQ);
					ppbBoQ.setId(ppbBoQId);

				}

				// Structure Distribution
				List<ProjectPlanBoqDistribution> ppbStructureDistributionList = projectPlanBoqDao
						.fetchProjectPlanBoqDistributionByProjectPlanBoqId(ppbStructure.getId());

				List<ProjectPlanBoqDistribution> distributionListToSave = new ArrayList<ProjectPlanBoqDistribution>();
				List<ProjectPlanBoqDistribution> distributionListToUpdate = new ArrayList<ProjectPlanBoqDistribution>();

				List<ProjectPlanBoqDistributionAddUpdateRequest> boqOrStDistributionAddUpdateList = new ArrayList<>();

				Double totalDistributedQuantity = 0.0;

				if (ppbStructureDistributionList != null && !ppbStructureDistributionList.isEmpty())
					for (ProjectPlanBoqDistribution dbObj : ppbStructureDistributionList) {

						for (ProjectPlanBoqDistributionAddUpdateRequest reqObj : requestDTO.getDistributionList()) {

							if (dbObj.getMonth().equals(reqObj.getMonth())
									&& dbObj.getYear().equals(reqObj.getYear())) {

								if (dbObj.getQuantityDistributed().equals(reqObj.getQuantityDistributed())) {
									totalDistributedQuantity = totalDistributedQuantity
											+ dbObj.getQuantityDistributed();
									break;
								} else {
									totalDistributedQuantity = totalDistributedQuantity
											+ reqObj.getQuantityDistributed();
									distributionListToUpdate
											.add(new ProjectPlanBoqDistribution(dbObj.getId(), ppbStructure.getId(),
													dbObj.getYear(), dbObj.getMonth(), reqObj.getQuantityDistributed(),
													new Date(), requestDTO.getUserDetail().getId().intValue()));

									boqOrStDistributionAddUpdateList.add(new ProjectPlanBoqDistributionAddUpdateRequest(
											dbObj.getYear(), dbObj.getMonth(),
											reqObj.getQuantityDistributed() - dbObj.getQuantityDistributed()));

									break;
								}

							}

						}

					}

				for (ProjectPlanBoqDistributionAddUpdateRequest reqObj : requestDTO.getDistributionList()) {

					Boolean hasFound = false;
					if (ppbStructureDistributionList != null && !ppbStructureDistributionList.isEmpty())
						for (ProjectPlanBoqDistribution dbObj : ppbStructureDistributionList) {
							if (dbObj.getMonth().equals(reqObj.getMonth())
									&& dbObj.getYear().equals(reqObj.getYear())) {
								hasFound = true;
								break;

							}
						}
					if (!hasFound) {
						totalDistributedQuantity = totalDistributedQuantity + reqObj.getQuantityDistributed();
						distributionListToSave.add(new ProjectPlanBoqDistribution(null, ppbStructure.getId(),
								reqObj.getYear(), reqObj.getMonth(), reqObj.getQuantityDistributed(), new Date(),
								requestDTO.getUserDetail().getId().intValue()));

						boqOrStDistributionAddUpdateList.add(new ProjectPlanBoqDistributionAddUpdateRequest(
								reqObj.getYear(), reqObj.getMonth(), reqObj.getQuantityDistributed()));

					}

				}

				// Structure Type Distribution
				List<ProjectPlanBoqDistribution> ppbStructureTypeDistributionList = projectPlanBoqDao
						.fetchProjectPlanBoqDistributionByProjectPlanBoqId(ppbStructureType.getId());

				if (ppbStructureTypeDistributionList != null && !ppbStructureTypeDistributionList.isEmpty())
					for (ProjectPlanBoqDistribution dbObj : ppbStructureTypeDistributionList) {

						for (ProjectPlanBoqDistributionAddUpdateRequest reqObj : boqOrStDistributionAddUpdateList) {

							if (dbObj.getMonth().equals(reqObj.getMonth())
									&& dbObj.getYear().equals(reqObj.getYear())) {

								distributionListToUpdate.add(new ProjectPlanBoqDistribution(dbObj.getId(),
										ppbStructureType.getId(), dbObj.getYear(), dbObj.getMonth(),
										dbObj.getQuantityDistributed() + reqObj.getQuantityDistributed(), new Date(),
										requestDTO.getUserDetail().getId().intValue()));
								break;

							}

						}

					}

				for (ProjectPlanBoqDistributionAddUpdateRequest reqObj : boqOrStDistributionAddUpdateList) {

					Boolean hasFound = false;
					if (ppbStructureTypeDistributionList != null && !ppbStructureTypeDistributionList.isEmpty())
						for (ProjectPlanBoqDistribution dbObj : ppbStructureTypeDistributionList) {
							if (dbObj.getMonth().equals(reqObj.getMonth())
									&& dbObj.getYear().equals(reqObj.getYear())) {
								hasFound = true;
								break;

							}
						}
					if (!hasFound) {
						distributionListToSave.add(new ProjectPlanBoqDistribution(null, ppbStructureType.getId(),
								reqObj.getYear(), reqObj.getMonth(), reqObj.getQuantityDistributed(), new Date(),
								requestDTO.getUserDetail().getId().intValue()));

					}

				}

				// BoQ Distribution
				List<ProjectPlanBoqDistribution> ppbBoqDistributionList = projectPlanBoqDao
						.fetchProjectPlanBoqDistributionByProjectPlanBoqId(ppbBoQ.getId());

				if (ppbBoqDistributionList != null && !ppbBoqDistributionList.isEmpty())
					for (ProjectPlanBoqDistribution dbObj : ppbBoqDistributionList) {

						for (ProjectPlanBoqDistributionAddUpdateRequest reqObj : boqOrStDistributionAddUpdateList) {

							if (dbObj.getMonth().equals(reqObj.getMonth())
									&& dbObj.getYear().equals(reqObj.getYear())) {

								distributionListToUpdate.add(new ProjectPlanBoqDistribution(dbObj.getId(),
										ppbBoQ.getId(), dbObj.getYear(), dbObj.getMonth(),
										dbObj.getQuantityDistributed() + reqObj.getQuantityDistributed(), new Date(),
										requestDTO.getUserDetail().getId().intValue()));
								break;

							}

						}

					}

				for (ProjectPlanBoqDistributionAddUpdateRequest reqObj : boqOrStDistributionAddUpdateList) {

					Boolean hasFound = false;
					if (ppbBoqDistributionList != null && !ppbBoqDistributionList.isEmpty())
						for (ProjectPlanBoqDistribution dbObj : ppbBoqDistributionList) {
							if (dbObj.getMonth().equals(reqObj.getMonth())
									&& dbObj.getYear().equals(reqObj.getYear())) {
								hasFound = true;
								break;

							}
						}
					if (!hasFound) {
						distributionListToSave.add(new ProjectPlanBoqDistribution(null, ppbBoQ.getId(),
								reqObj.getYear(), reqObj.getMonth(), reqObj.getQuantityDistributed(), new Date(),
								requestDTO.getUserDetail().getId().intValue()));

					}

				}

				List<StructureBoqQuantityMapping> structureBoqQuantityMappingList = structureBoqQuantityDao
						.fetchStructureBoqMappingByBoqIdStructureIdAndTypeId(requestDTO.getBoqId(),
								requestDTO.getStructureId(), null, requestDTO.getSiteId().longValue());

				Double structureBoqQuantity = 0.0;

				if (structureBoqQuantityMappingList != null)
					for (StructureBoqQuantityMapping structureBoqQuantityMapping : structureBoqQuantityMappingList) {
						structureBoqQuantity = structureBoqQuantity + structureBoqQuantityMapping.getQuantity();
					}

				if (totalDistributedQuantity > structureBoqQuantity) {
					return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null,
							"Distributed quantity is greater than total quantity.");
				}
				for (ProjectPlanBoqDistribution projectPlanBoqDistribution : distributionListToSave) {

					projectPlanBoqDao.saveProjectPlantBoqDistribution(projectPlanBoqDistribution);
				}
				for (ProjectPlanBoqDistribution projectPlanBoqDistribution : distributionListToUpdate) {

					projectPlanBoqDao.updateProjectPlantBoqDistribution(projectPlanBoqDistribution);
				}
				return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());

			}

			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getProjectPlanBoq(ProjectPlanBoqFetchRequest requestDTO) {
		try {

			if (requestDTO.getWorkType().equals(WorkType.HIGHWAY)) {

				Site site = siteDao.fetchById(requestDTO.getSiteId().longValue());

				if (site == null || site.getStartDate() == null || site.getEndDate() == null) {
					return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null,
							site == null ? "Site not found." : "Start/End Date is not present.");
				}

				List<HighwayBoqMapping> highwayBoqMappingList = highwayBoqMapDaoImpl
						.fetchByBoqAndSite(requestDTO.getBoqId(), requestDTO.getSiteId().longValue());

				if (highwayBoqMappingList == null || highwayBoqMappingList.isEmpty()) {
					return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "Highway BoQ Mapping not found.");
				}
				HighwayBoqMapping highwayBoqMapping = highwayBoqMappingList.get(0);
				ProjectPlanBoqResponse responseObj = setObject
						.highwayBoqMappingEntityToProjectPlanBoqResponse(highwayBoqMapping);
				responseObj.setStartDate(site.getStartDate());
				responseObj.setEndDate(site.getEndDate());

				Double totalQuantity = 0.0;

				ProjectPlanBoq projectPlanBoqDbObj = projectPlanBoqDao.fetchProjectPlanBoqByWorkTypeAndBoqId(
						requestDTO.getWorkType(), requestDTO.getBoqId(), null, null);

				List<ProjectPlanBoqDistribution> ppbDistributionList = null;

				if (projectPlanBoqDbObj != null) {
					ppbDistributionList = projectPlanBoqDao
							.fetchProjectPlanBoqDistributionByProjectPlanBoqId(projectPlanBoqDbObj.getId());
				}

				for (HighwayBoqMapping hbq : highwayBoqMappingList) {
					totalQuantity = totalQuantity + hbq.getQuantity();
				}

				responseObj.setTotalQuantity(totalQuantity);

				Map<Date, Double> monthlyAllocatedQuantity = new LinkedHashMap<>();

				Calendar fromCalendar = Calendar.getInstance();
				fromCalendar.setTime(site.getStartDate());
				fromCalendar.set(fromCalendar.get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH), 1);
				Calendar toCalendar = Calendar.getInstance();
				toCalendar.setTime(site.getEndDate());
				toCalendar.set(toCalendar.get(Calendar.YEAR), toCalendar.get(Calendar.MONTH),
						toCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				while (fromCalendar.before(toCalendar)) {
					Double allocatedQuantity = 0.0;
					if (ppbDistributionList != null && !ppbDistributionList.isEmpty()) {
						for (ProjectPlanBoqDistribution ppbDistribution : ppbDistributionList) {
							if (ppbDistribution.getMonth().equals(fromCalendar.get(Calendar.MONTH))
									&& ppbDistribution.getYear().equals(fromCalendar.get(Calendar.YEAR))) {
								allocatedQuantity = ppbDistribution.getQuantityDistributed();
							}
						}
					}
					monthlyAllocatedQuantity.put(fromCalendar.getTime(), allocatedQuantity);
					fromCalendar.add(Calendar.MONTH, 1);
				}
				responseObj.setMonthlyAllocatedQuantity(monthlyAllocatedQuantity);

				return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());
			}

			if (requestDTO.getWorkType().equals(WorkType.STRUCTURE)) {

				Site site = siteDao.fetchById(requestDTO.getSiteId().longValue());

				if (site == null || site.getStartDate() == null || site.getEndDate() == null) {
					return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null,
							site == null ? "Site not found." : "Start/End Date is not present.");
				}

				List<StructureBoqQuantityMapping> structureBoqMappingList = structureBoqQuantityDao
						.fetchStructureBoqMappingByBoqIdStructureIdAndTypeId(requestDTO.getBoqId(),
								requestDTO.getStructureId(), requestDTO.getStructureTypeId(),
								requestDTO.getSiteId().longValue());

				if (structureBoqMappingList == null || structureBoqMappingList.isEmpty()) {
					return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "Structure BoQ Mapping not found.");
				}
				StructureBoqQuantityMapping structureBoqMapping = structureBoqMappingList.get(0);
				ProjectPlanBoqResponse responseObj = setObject
						.structureBoqMappingEntityToProjectPlanBoqResponse(structureBoqMapping);
				responseObj.setStartDate(site.getStartDate());
				responseObj.setEndDate(site.getEndDate());

				Double totalQuantity = 0.0;

				ProjectPlanBoq projectPlanBoqDbObj = projectPlanBoqDao.fetchProjectPlanBoqByWorkTypeAndBoqId(
						requestDTO.getWorkType(), requestDTO.getBoqId(), requestDTO.getStructureTypeId(),
						requestDTO.getStructureId());

				List<ProjectPlanBoqDistribution> ppbDistributionList = null;

				if (projectPlanBoqDbObj != null) {
					ppbDistributionList = projectPlanBoqDao
							.fetchProjectPlanBoqDistributionByProjectPlanBoqId(projectPlanBoqDbObj.getId());
				}

				for (StructureBoqQuantityMapping sbq : structureBoqMappingList) {
					totalQuantity = totalQuantity + sbq.getQuantity();
				}

				responseObj.setTotalQuantity(totalQuantity);

				Map<Date, Double> monthlyAllocatedQuantity = new LinkedHashMap<>();

				Calendar fromCalendar = Calendar.getInstance();
				fromCalendar.setTime(site.getStartDate());
				fromCalendar.set(fromCalendar.get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH), 1);
				Calendar toCalendar = Calendar.getInstance();
				toCalendar.setTime(site.getEndDate());
				toCalendar.set(toCalendar.get(Calendar.YEAR), toCalendar.get(Calendar.MONTH),
						toCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				while (fromCalendar.before(toCalendar)) {
					Double allocatedQuantity = 0.0;
					if (ppbDistributionList != null && !ppbDistributionList.isEmpty()) {
						for (ProjectPlanBoqDistribution ppbDistribution : ppbDistributionList) {
							if (ppbDistribution.getMonth().equals(fromCalendar.get(Calendar.MONTH))
									&& ppbDistribution.getYear().equals(fromCalendar.get(Calendar.YEAR))) {
								allocatedQuantity = ppbDistribution.getQuantityDistributed();
							}
						}
					}
					monthlyAllocatedQuantity.put(fromCalendar.getTime(), allocatedQuantity);
					fromCalendar.add(Calendar.MONTH, 1);
				}
				responseObj.setMonthlyAllocatedQuantity(monthlyAllocatedQuantity);

				return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());
			}
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getStructureTypeAndStructureByBoqId(Long boqId, Integer siteId) {
		try {

			List<StructureBoqQuantityMapping> structureBoqMappingList = structureBoqQuantityDao
					.fetchStructureBoqMappingByBoqIdStructureIdAndTypeId(boqId, null, null, siteId.longValue());
			Map<Long, List<StructureBoqQuantityMapping>> structureTypeMap = structureBoqMappingList.stream()
					.collect(Collectors.groupingBy(e -> e.getStructure().getType().getId()));

			List<StructureTypeWithStructuresFetchResponse> responseObj = new ArrayList<>();

			for (Map.Entry<Long, List<StructureBoqQuantityMapping>> entry : structureTypeMap.entrySet()) {
				StructureType st = entry.getValue().get(0).getStructure().getType();
				StructureTypeWithStructuresFetchResponse stWithStructure = new StructureTypeWithStructuresFetchResponse(
						st.getId(), st.getName(), null);

				List<IdNameDTO> stuructureList = new ArrayList<>();
				for (StructureBoqQuantityMapping obj : entry.getValue()) {

					stuructureList.add(new IdNameDTO(obj.getStructure().getId(), obj.getStructure().getName()));

				}
				stWithStructure.setStructureList(stuructureList);

				responseObj.add(stWithStructure);
			}

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

}
