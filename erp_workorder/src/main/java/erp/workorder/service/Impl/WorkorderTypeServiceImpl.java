package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderTypeDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WorkorderTypeDTO;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderType;
import erp.workorder.enums.Responses;
import erp.workorder.service.WorkorderTypeService;
import erp.workorder.util.SetObject;

@Transactional
@Service
public class WorkorderTypeServiceImpl implements WorkorderTypeService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private WorkorderTypeDao woTypeDao;
	@Autowired
	private WorkorderDao workorderDao;

	@Override
	public CustomResponse getWorkorderTypes(SearchDTO search) {

		try {
			List<WorkorderType> types = woTypeDao.fetchWorkorderTypes(search);
			List<WorkorderTypeDTO> obj = new ArrayList<>();
			if (types != null)
				types.forEach(type -> obj.add(setObject.workorderTypeEntityToDto(type)));
			return new CustomResponse(Responses.SUCCESS.getCode(), obj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addWorkorderType(WorkorderTypeDTO typeDTO) {

		try {
			if (typeDTO.getName() == null || typeDTO.getName().isEmpty() || typeDTO.getCode() == null
					|| typeDTO.getCode().isEmpty()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
			}
			WorkorderType type = setObject.workorderTypeDtoToEntity(typeDTO);
			type.setModifiedOn(new Date());
			type.setIsActive(true);
			Long id = woTypeDao.saveWorkorderType(type);
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
	public CustomResponse updateWorkorderType(WorkorderTypeDTO typeDTO) {

		try {
			if (typeDTO.getName() == null || typeDTO.getName().isEmpty() || typeDTO.getCode() == null
					|| typeDTO.getCode().isEmpty()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
			}
			WorkorderType type = woTypeDao.fetchWorkorderTypeById(typeDTO.getId());
			type.setCode(typeDTO.getCode());
			type.setName(typeDTO.getName());
			type.setModifiedOn(new Date());
			Boolean updateType = woTypeDao.updateWorkorderType(type);
			return new CustomResponse(Responses.SUCCESS.getCode(), updateType, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getWorkorderTypeById(SearchDTO search) {

		try {
			WorkorderType type = woTypeDao.fetchWorkorderTypeById(search.getId().intValue());
			WorkorderTypeDTO result = setObject.workorderTypeEntityToDto(type);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse removeWorkorderType(SearchDTO search) {

		try {
			WorkorderType type = woTypeDao.fetchWorkorderTypeById(search.getId().intValue());
			List<Workorder> workorders = workorderDao.fetchWorkordersByTypeId(search.getId());
			if (workorders != null && workorders.size() > 0) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), false, Responses.FORBIDDEN.toString());
			}
			type.setIsActive(false);
			type.setModifiedBy(search.getUserId());
			type.setModifiedOn(new Date());
			Boolean updateType = woTypeDao.updateWorkorderType(type);
			return new CustomResponse(Responses.SUCCESS.getCode(), updateType, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
