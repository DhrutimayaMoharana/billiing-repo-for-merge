package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import erp.boq_mgmt.dao.StructureBoqQuantityDao;
import erp.boq_mgmt.dao.StructureDao;
import erp.boq_mgmt.dao.StructureGroupDao;
import erp.boq_mgmt.dao.StructureTypeDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.StructureDTO;
import erp.boq_mgmt.dto.TypeStructureParentChildRenderDTO;
import erp.boq_mgmt.dto.TypeWiseStructureRenderDTO;
import erp.boq_mgmt.dto.response.IdNameDTO;
import erp.boq_mgmt.dto.response.TypeWiseStructureIdsDTO;
import erp.boq_mgmt.entity.Structure;
import erp.boq_mgmt.entity.StructureBoqQuantityMapping;
import erp.boq_mgmt.entity.StructureGroup;
import erp.boq_mgmt.entity.StructureType;
import erp.boq_mgmt.entity.StructureV2;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.StructureService;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class StructureServiceImpl implements StructureService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StructureDao structureDao;
	@Autowired
	private SetObject setObject;
	@Autowired
	private StructureTypeDao structureTypeDao;
	@Autowired
	private StructureBoqQuantityDao sbqDao;

	@Autowired
	private StructureGroupDao structureGroupDao;

	public Integer getCount(SearchDTO search) {
		return structureDao.fetchCount(search);
	}

	@Override
	public CustomResponse getStructures(SearchDTO search) {

		try {
			List<Structure> structures = structureDao.fetchStructures(search);
			List<StructureDTO> obj = new ArrayList<>();
			if (structures != null)
				structures.forEach(item -> obj.add(setObject.structureEntityToDto(item)));
			Integer count = getCount(search);
			PaginationDTO resultObj = new PaginationDTO(count, obj);
			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}

	}

	@Override
	public CustomResponse addStructure(StructureDTO structureDTO) {

		try {

			if (structureDTO.getType() == null || structureDTO.getType().getId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide type.");
			}
			if (structureDTO.getIsGroupBased() != null && structureDTO.getIsGroupBased()) {

				if (structureDTO.getGroupId() == null || structureDTO.getGroupId() <= 0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide group.");
				}
				if (structureDTO.getStructureCount() == null || structureDTO.getStructureCount() <= 0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide structure count.");
				}

				StructureType type = structureTypeDao.fetchStructureTypeByIdOrName(structureDTO.getType().getId(),
						null);
				if (type == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid type.");
				}
				StructureGroup group = structureGroupDao.fetchGroupById(structureDTO.getGroupId());
				if (group == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid group.");
				}
				String separator = ":";
				String groupStructureMultiplesPattern = group.getName() + separator;
				Integer maxExistedGroupStructurePatternIndex = structureDao.fetchMaxExistedGroupStructurePatternIndex(
						structureDTO.getSiteId(), groupStructureMultiplesPattern, separator);

				for (int i = 0; i < structureDTO.getStructureCount(); i++) {
					Structure structure = setObject.structureDtoToEntity(structureDTO);
					structure.setName(groupStructureMultiplesPattern + (++maxExistedGroupStructurePatternIndex));
					structure.setCreatedOn(new Date());
					structure.setModifiedOn(new Date());
					structure.setModifiedBy(structure.getCreatedBy());
					structureDao.saveStructure(structure);
				}

				return new CustomResponse(Responses.SUCCESS.getCode(), null, "Added.");

			} else {

				Structure structure = setObject.structureDtoToEntity(structureDTO);
				if (structure.getType() == null || structure.getType().getId() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide type.");
				}
				if (structure.getName() == null || structure.getName().isBlank()) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
				}
				if (structure.getStartChainage() == null || structure.getStartChainage().getId() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide start chainage.");
				}

				structure.setCreatedOn(new Date());
				structure.setModifiedOn(new Date());
				structure.setModifiedBy(structure.getCreatedBy());
				Long id = structureDao.saveStructure(structure);
				return new CustomResponse(Responses.SUCCESS.getCode(), id,
						((id != null && id.longValue() > 0L) ? "Added." : "Already exists."));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}

	}

	@Override
	public CustomResponse updateStructure(StructureDTO structureDTO) {

		try {
			if (structureDTO.getName() == null || structureDTO.getName().trim().isEmpty()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
			}
			if (structureDTO.getType() == null || structureDTO.getType().getId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide type.");
			}
			if (structureDTO.getStartChainage() == null || structureDTO.getStartChainage().getId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide start chainage.");
			}
			Structure structure = setObject.updatedStructure(structureDao.fetchStructureById(structureDTO.getId()),
					structureDTO);
			structureDao.updateStructure(structure);
			return new CustomResponse(Responses.SUCCESS.getCode(), structureDTO.getId(), "Updated.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getStructureById(SearchDTO search) {

		try {
			Structure structure = structureDao.fetchStructureById(search.getId());
			StructureDTO result = setObject.structureEntityToDto(structure);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public CustomResponse getTypeWiseStructures(SearchDTO search) {

		try {
			List<StructureType> types = structureTypeDao.fetchStructureTypes(search);
			List<Structure> structures = structureDao.fetchStructures(search);
			List<StructureBoqQuantityMapping> sbqs = sbqDao.fetchStructureBoqQuantityMappingBySearch(search);
			List<TypeStructureParentChildRenderDTO> pcList = new ArrayList<>();

			if (types != null && structures != null) {
				int uniquePid = 0;
				for (StructureType type : types) {
					TypeStructureParentChildRenderDTO parent = new TypeStructureParentChildRenderDTO(type.getId(),
							type.getName(), type.getCode(), null, null, null, null, null, 0, ++uniquePid, null);
					int structureCount = 0;
					for (Structure structure : structures) {
						if (type.getId().equals(structure.getType().getId())) {
							structureCount++;
							TypeStructureParentChildRenderDTO child = new TypeStructureParentChildRenderDTO(
									structure.getId(), structure.getName(), null, structure.getGroupId(),
									structure.getGroup() != null ? structure.getGroup().getName() : null,
									structure.getStartChainage() != null ? structure.getStartChainage().getId() : null,
									structure.getStartChainage() != null ? structure.getStartChainage().getName()
											: null,
									0, null, null, uniquePid);
							if (sbqs != null) {
								int boqCount = 0;
								for (StructureBoqQuantityMapping sbq : sbqs) {
									if (sbq.getStructure().getId().equals(structure.getId())) {
										boqCount++;
									}
								}
								child.setBoqCount(boqCount);
							}
							pcList.add(child);
						}
					}
					if (structureCount > 0) {
						parent.setStructureCount(structureCount);
						pcList.add(parent);
					}
				}
			}

			TypeWiseStructureRenderDTO result = new TypeWiseStructureRenderDTO(pcList);

			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse removeStructure(SearchDTO search) {

		try {
			if (search.getStructureId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, Responses.BAD_REQUEST.toString());
			}
			Structure structure = structureDao.fetchStructureById(search.getStructureId());
			structure.setModifiedOn(new Date());
			structure.setIsActive(false);
			structure.setModifiedBy(search.getUserId());
			Boolean result = structureDao.updateStructure(structure);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}

	}

	@Override
	public CustomResponse getTypeStructures(SearchDTO search) {

		try {

			if (search.getSiteId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide siteId.");
			}
			List<StructureV2> structures = structureDao.fetchTypeWiseStructureIds(search);
			List<TypeWiseStructureIdsDTO> resultList = new ArrayList<>();
			Set<Long> distinctTypeIds = new HashSet<Long>();
			if (structures != null) {
				for (StructureV2 structure : structures) {
					if (structure.getType() != null)
						distinctTypeIds.add(structure.getType().getId());
				}

				for (Long typeId : distinctTypeIds) {
					Set<Long> structureIds = new LinkedHashSet<Long>();
					TypeWiseStructureIdsDTO type = new TypeWiseStructureIdsDTO(typeId);
					for (StructureV2 structure : structures) {
						if (typeId.equals(structure.getType().getId())) {
							if (type.getName() == null) {
								type.setName(structure.getType().getName());
							}
							structureIds.add(structure.getId());
						}
					}
					type.setStructureIds(structureIds);
					if (structureIds.size() > 0)
						resultList.add(type);
				}

			}
			return new CustomResponse(Responses.SUCCESS.getCode(), resultList, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getGroupStructures(Integer groupId) {

		try {
			List<Structure> structures = structureDao.fetchStructuresByGroupId(groupId);
			List<IdNameDTO> resultObj = new ArrayList<>();
			if (structures != null) {
				for (Structure obj : structures) {
					resultObj.add(new IdNameDTO(obj.getId(), obj.getName()));
				}
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());

		}
	}

}
