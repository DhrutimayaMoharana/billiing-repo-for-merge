package erp.billing.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.billing.dao.WorkorderLabourTypeDao;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.request.WorkorderLabourTypeAddUpdateRequest;
import erp.billing.dto.response.PaginationResultObject;
import erp.billing.dto.response.WorkorderLabourTypeFetchResponse;
import erp.billing.entity.WorkorderLabourType;
import erp.billing.enums.Responses;
import erp.billing.service.WorkorderLabourTypeService;
import erp.billing.util.CustomValidationUtil;
import erp.billing.util.SetObject;

@Transactional(rollbackFor = Exception.class)
@Service
public class WorkorderLabourTypeServiceImpl implements WorkorderLabourTypeService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private WorkorderLabourTypeDao workorderLabourTypeDao;
	@Autowired
	private CustomValidationUtil validate;

	@Override
	public CustomResponse addOrUpdateWorkorderLabourType(WorkorderLabourTypeAddUpdateRequest requestObj) {
		try {

			if (requestObj.getId() == null) {

				CustomResponse validateAddRequest = validate.addLabourType(requestObj);
				if (validateAddRequest.getStatus().equals(Responses.BAD_REQUEST.getCode())) {
					return validateAddRequest;
				}

				WorkorderLabourType workorderLabourType = setObject
						.workorderLabourTypeAddOrUpdateRequestDtoToEntity(requestObj);

				Integer id = workorderLabourTypeDao.saveWorkorderLabourType(workorderLabourType);

				return new CustomResponse(
						((id != null && id.longValue() > 0) ? Responses.SUCCESS.getCode()
								: Responses.FORBIDDEN.getCode()),
						id, ((id != null && id.longValue() > 0) ? Responses.SUCCESS.toString() : "Already exists."));

			} else {

				CustomResponse validateUpdateRequest = validate.updateLabourType(requestObj);
				if (validateUpdateRequest.getStatus().equals(Responses.BAD_REQUEST.getCode())) {
					return validateUpdateRequest;
				}

				WorkorderLabourType workorderLabourType = setObject
						.workorderLabourTypeAddOrUpdateRequestDtoToEntity(requestObj);
				WorkorderLabourType dbObj = workorderLabourTypeDao.fetchworkorderLabourTypeById(requestObj.getId());
				if (dbObj == null)
					return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Invalid id.");

				workorderLabourType.setIsActive(dbObj.getIsActive());
				workorderLabourType.setCreatedBy(dbObj.getCreatedBy());
				workorderLabourType.setCreatedOn(dbObj.getCreatedOn());

				Boolean isUpdated = workorderLabourTypeDao.updateWorkorderLabourType(workorderLabourType);

				return new CustomResponse(isUpdated ? Responses.SUCCESS.getCode() : Responses.FORBIDDEN.getCode(),
						isUpdated, isUpdated ? Responses.SUCCESS.toString() : "Does not saved.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}

	}

	@Override
	public CustomResponse getWorkorderLabourTypeList(Integer companyId) {
		try {

			List<WorkorderLabourType> workorderLabourType = workorderLabourTypeDao
					.fetchWorkorderLabourTypeDaoList(companyId);
			List<WorkorderLabourTypeFetchResponse> workorderLabourTypeResObj = new ArrayList<>();

			if (workorderLabourType != null)
				workorderLabourType.forEach(wld -> workorderLabourTypeResObj
						.add(setObject.workorderLabourTypeEntityToFetchResponseDto(wld)));

			PaginationResultObject resultObject = new PaginationResultObject(
					workorderLabourType != null ? workorderLabourType.size() : 0, workorderLabourTypeResObj);

			return new CustomResponse(Responses.SUCCESS.getCode(), resultObject, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	@Override
	public CustomResponse deactivateWorkorderLabourType(Integer id, Long userId) {
		try {

			WorkorderLabourType workorderLabourType = workorderLabourTypeDao.fetchworkorderLabourTypeById(id);
			if (workorderLabourType == null)
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Does not exist.");

			workorderLabourType.setIsActive(false);
			workorderLabourType.setModifiedOn(new Date());
			workorderLabourType.setModifiedBy(userId.intValue());
			Boolean isDeactivated = workorderLabourTypeDao.deactivateWorkorderLabourType(workorderLabourType);

			return new CustomResponse(isDeactivated ? Responses.SUCCESS.getCode() : Responses.FORBIDDEN.getCode(),
					isDeactivated, isDeactivated ? Responses.SUCCESS.toString() : Responses.FORBIDDEN.toString());

		} catch (

		Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	@Override
	public CustomResponse getWorkorderLabourTypeById(Integer id) {
		try {

			WorkorderLabourType workorderLabourType = workorderLabourTypeDao.fetchworkorderLabourTypeById(id);
			WorkorderLabourTypeFetchResponse resultObj = setObject
					.workorderLabourTypeEntityToFetchResponseDto(workorderLabourType);

			return new CustomResponse(resultObj != null ? Responses.SUCCESS.getCode() : Responses.NOT_FOUND.getCode(),
					resultObj, resultObj != null ? Responses.SUCCESS.toString() : "Invalid id.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

}
