package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.boq_mgmt.dao.StructureDao;
import erp.boq_mgmt.dao.StructureTypeDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.StructureTypeDTO;
import erp.boq_mgmt.entity.Structure;
import erp.boq_mgmt.entity.StructureType;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.StructureTypeService;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class StructureTypeServiceImpl implements StructureTypeService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StructureTypeDao structTypeDao;
	@Autowired
	private StructureDao structureDao;
	@Autowired
	private SetObject setObject;

	public Integer getCount(SearchDTO search) {
		return structTypeDao.fetchCount(search);
	}

	@Override
	public CustomResponse addStructureType(StructureTypeDTO typeDTO) {

		try {
			StructureType type = setObject.structureTypeDtoToEntity(typeDTO);
			if (type.getName() == null || type.getName().isEmpty())
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Name is not valid.");
			if (type.getCode() == null || type.getCode().isEmpty())
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Code is not valid.");
			type.setCreatedOn(new Date());
			type.setModifiedOn(new Date());
			type.setIsActive(true);
			type.setModifiedBy(typeDTO.getCreatedBy());
			Long id = structTypeDao.saveStructureType(type);
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
	public CustomResponse updateStructureType(StructureTypeDTO typeDTO) {

		try {
			StructureType type = setObject.updatedStructureType(
					structTypeDao.fetchStructureTypeByIdOrName(typeDTO.getId(), null),
					setObject.structureTypeDtoToEntity(typeDTO));
			if (type.getName() == null || type.getName().isEmpty())
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Name is not valid.");
			if (type.getCode() == null || type.getCode().isEmpty())
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Code is not valid.");
			Boolean updateType = structTypeDao.updateStructureType(type);
			return new CustomResponse(Responses.SUCCESS.getCode(), updateType, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getStructureTypeByIdOrName(SearchDTO search) {

		try {
			StructureType type = structTypeDao.fetchStructureTypeByIdOrName(search.getId(), search.getName());
			StructureTypeDTO result = setObject.structureTypeEntityToDto(type);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getStructureTypes(SearchDTO search) {

		try {
			List<StructureType> items = structTypeDao.fetchStructureTypes(search);
			List<StructureTypeDTO> obj = new ArrayList<>();
			if (items != null)
				items.forEach(item -> obj.add(setObject.structureTypeEntityToDto(item)));
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
	public CustomResponse removeStructureType(SearchDTO search) {

		try {
			StructureType type = structTypeDao.fetchStructureTypeByIdOrName(search.getId(), null);
			List<Structure> structures = structureDao.fetchStructuresByTypeId(search.getId());
			if (structures != null && structures.size() > 0) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), false, Responses.FORBIDDEN.toString());
			}
			type.setIsActive(false);
			type.setModifiedBy(search.getUserId());
			type.setModifiedOn(new Date());
			Boolean updateType = structTypeDao.updateStructureType(type);
			return new CustomResponse(Responses.SUCCESS.getCode(), updateType, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
