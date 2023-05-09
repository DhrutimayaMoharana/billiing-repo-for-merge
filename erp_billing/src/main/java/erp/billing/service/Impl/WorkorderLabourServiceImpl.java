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

import erp.billing.dao.WorkorderLabourDao;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.request.WorkorderLabourAddUpdateRequest;
import erp.billing.dto.request.WorkorderLabourFetchRequest;
import erp.billing.dto.response.PaginationResultObject;
import erp.billing.dto.response.WorkorderLabourFetchResponse;
import erp.billing.entity.WorkorderLabour;
import erp.billing.enums.Responses;
import erp.billing.service.WorkorderLabourService;
import erp.billing.util.CustomValidationUtil;
import erp.billing.util.SetObject;

@Transactional(rollbackFor = Exception.class)
@Service
public class WorkorderLabourServiceImpl implements WorkorderLabourService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private WorkorderLabourDao workorderLabourDao;
	@Autowired
	private CustomValidationUtil validate;

	@Override
	public CustomResponse addOrUpdateWorkorderLabour(WorkorderLabourAddUpdateRequest requestObj) {
		try {

			if (requestObj.getId() == null) {

				CustomResponse validateAddRequest = validate.addWorkorderLabour(requestObj);
				if (validateAddRequest.getStatus().equals(Responses.BAD_REQUEST.getCode())) {
					return validateAddRequest;
				}

				WorkorderLabour workorderLabour = setObject.workorderLabourAddOrUpdateRequestDtoToEntity(requestObj);

				Integer id = workorderLabourDao.saveWorkorderLabour(workorderLabour);

				return new CustomResponse(
						((id != null && id.longValue() > 0) ? Responses.SUCCESS.getCode()
								: Responses.FORBIDDEN.getCode()),
						id, ((id != null && id.longValue() > 0) ? Responses.SUCCESS.toString() : "Already exists."));

			} else {

				CustomResponse validateUpdateRequest = validate.updateWorkorderLabour(requestObj);
				if (validateUpdateRequest.getStatus().equals(Responses.BAD_REQUEST.getCode())) {
					return validateUpdateRequest;
				}

				WorkorderLabour workorderLabour = setObject.workorderLabourAddOrUpdateRequestDtoToEntity(requestObj);
				WorkorderLabour dbObj = workorderLabourDao.fetchworkorderLabourById(requestObj.getId());
				if (dbObj == null)
					return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Invalid id.");

				workorderLabour.setIsActive(dbObj.getIsActive());
				workorderLabour.setCreatedBy(dbObj.getCreatedBy());
				workorderLabour.setCreatedOn(dbObj.getCreatedOn());

				Boolean isUpdated = workorderLabourDao.updateWorkorderLabourDepartment(workorderLabour);

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
	public CustomResponse getWorkorderLabourList(WorkorderLabourFetchRequest requestObj) {
		try {

			List<WorkorderLabour> workorderLabour = workorderLabourDao.fetchWorkorderLabourDaoList(requestObj);
			List<WorkorderLabourFetchResponse> workorderLabourResObj = new ArrayList<>();

			if (workorderLabour != null)
				workorderLabour.forEach(
						wl -> workorderLabourResObj.add(setObject.workorderLabourEntityToFetchResponseDto(wl)));

			PaginationResultObject resultObject = new PaginationResultObject(
					workorderLabourResObj != null ? workorderLabourResObj.size() : 0, workorderLabourResObj);

			return new CustomResponse(Responses.SUCCESS.getCode(), resultObject, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	@Override
	public CustomResponse getWorkorderLabourById(Integer id) {
		try {

			WorkorderLabour workorderLabour = workorderLabourDao.fetchworkorderLabourById(id);
			WorkorderLabourFetchResponse resultObj = setObject.workorderLabourEntityToFetchResponseDto(workorderLabour);

			return new CustomResponse(resultObj != null ? Responses.SUCCESS.getCode() : Responses.NOT_FOUND.getCode(),
					resultObj, resultObj != null ? Responses.SUCCESS.toString() : "Invalid id.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	@Override
	public CustomResponse deactivateWorkorderLabour(Integer id, Long userId) {
		try {

			WorkorderLabour workorderLabour = workorderLabourDao.fetchworkorderLabourById(id);
			if (workorderLabour == null)
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Does not exist.");

			workorderLabour.setIsActive(false);
			workorderLabour.setModifiedOn(new Date());
			workorderLabour.setModifiedBy(userId.intValue());
			Boolean isDeactivated = workorderLabourDao.deactivateWorkorderLabourDepartment(workorderLabour);

			return new CustomResponse(isDeactivated ? Responses.SUCCESS.getCode() : Responses.FORBIDDEN.getCode(),
					isDeactivated, isDeactivated ? Responses.SUCCESS.toString() : Responses.FORBIDDEN.toString());

		} catch (

		Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

}
