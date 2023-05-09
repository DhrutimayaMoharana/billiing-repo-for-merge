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

import erp.workorder.dao.WorkorderTncDao;
import erp.workorder.dao.WorkorderTncTypeDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WoTncTypeDTO;
import erp.workorder.entity.WoTnc;
import erp.workorder.entity.WoTncType;
import erp.workorder.enums.Responses;
import erp.workorder.service.WorkorderTncTypeService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.SetObject;

@Transactional
@Service
public class WorkorderTncTypeServiceImpl implements WorkorderTncTypeService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private WorkorderTncTypeDao woTncTypeDao;
	@Autowired
	private WorkorderTncDao woTncDao;
	@Autowired
	private CustomValidationUtil validationUtil;

	@Override
	public CustomResponse getWorkorderTncTypes(SearchDTO search) {

		try {
			List<WoTncType> types = woTncTypeDao.fetchActiveWoTncTypes(search);
			List<WoTncTypeDTO> obj = new ArrayList<>();
			if (types != null)
				types.forEach(type -> obj.add(setObject.woTncTypeEntityToDto(type)));
			return new CustomResponse(Responses.SUCCESS.getCode(), obj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addWorkorderTncType(WoTncTypeDTO tncTypeDTO) {

		try {
			CustomResponse validationRes = validationUtil.validateAddWorkorderTncType(tncTypeDTO);
			if (validationRes.getStatus().equals(Responses.BAD_REQUEST.getCode()))
				return validationRes;

			WoTncType type = setObject.woTncTypeDtoToEntity(tncTypeDTO);
			type.setCreatedOn(new Date());
			type.setIsActive(true);
			Long id = woTncTypeDao.saveWoTncType(type);
			return new CustomResponse(Responses.SUCCESS.getCode(), null,
					((id != null && id.longValue() > 0L) ? "Added." : "Already exists."));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateWorkorderTncType(WoTncTypeDTO tncTypeDTO) {

		try {

			CustomResponse validationRes = validationUtil.validateUpdateWorkorderTncType(tncTypeDTO);
			if (validationRes.getStatus().equals(Responses.BAD_REQUEST.getCode()))
				return validationRes;

			WoTncType type = woTncTypeDao.fetchWoTncTypeById(tncTypeDTO.getId());
			type.setName(tncTypeDTO.getName());
			type.setCreatedOn(new Date());
			Boolean updateType = woTncTypeDao.updateWoTncType(type);
			return new CustomResponse(Responses.SUCCESS.getCode(), updateType, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getWorkorderTncTypeById(SearchDTO search) {

		try {
			WoTncType type = woTncTypeDao.fetchWoTncTypeById(search.getId());
			WoTncTypeDTO result = setObject.woTncTypeEntityToDto(type);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse removeWorkorderTncType(SearchDTO search) {

		try {
			WoTncType type = woTncTypeDao.fetchWoTncTypeById(search.getId());
			List<WoTnc> woTncs = woTncDao.fetchWoTncsByTncTypeId(search.getId());
			if (woTncs != null && woTncs.size() > 0) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), false, Responses.FORBIDDEN.toString());
			}
			type.setIsActive(false);
			type.setCreatedBy(search.getUserId());
			Boolean updateType = woTncTypeDao.updateWoTncType(type);
			return new CustomResponse(Responses.SUCCESS.getCode(), updateType, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
