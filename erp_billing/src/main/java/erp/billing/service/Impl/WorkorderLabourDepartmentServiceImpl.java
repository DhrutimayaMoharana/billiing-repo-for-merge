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

import erp.billing.dao.WorkorderLabourDepartmentDao;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.request.WorkorderLabourDepartmentAddUpdateRequest;
import erp.billing.dto.response.PaginationResultObject;
import erp.billing.dto.response.WorkorderLabourDepartmentFetchResponse;
import erp.billing.entity.WorkorderLabourDepartment;
import erp.billing.enums.Responses;
import erp.billing.service.WorkorderLabourDepartmentService;
import erp.billing.util.CustomValidationUtil;
import erp.billing.util.SetObject;

@Transactional(rollbackFor = Exception.class)
@Service
public class WorkorderLabourDepartmentServiceImpl implements WorkorderLabourDepartmentService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SetObject setObject;
	@Autowired
	private WorkorderLabourDepartmentDao workorderLabourDepartmentDao;
	@Autowired
	private CustomValidationUtil validate;

	@Override
	public CustomResponse addOrUpdateWorkorderLabourDepartment(WorkorderLabourDepartmentAddUpdateRequest requestObj) {
		try {

			if (requestObj.getId() == null) {

				CustomResponse validateAddRequest = validate.addLabourDepartment(requestObj);
				if (validateAddRequest.getStatus().equals(Responses.BAD_REQUEST.getCode())) {
					return validateAddRequest;
				}

				WorkorderLabourDepartment workorderLabourDepartment = setObject
						.workorderLabourDepartmentAddOrUpdateRequestDtoToEntity(requestObj);

				Integer id = workorderLabourDepartmentDao.saveWorkorderLabourDepartment(workorderLabourDepartment);

				return new CustomResponse(
						((id != null && id.longValue() > 0) ? Responses.SUCCESS.getCode()
								: Responses.FORBIDDEN.getCode()),
						id, ((id != null && id.longValue() > 0) ? Responses.SUCCESS.toString() : "Already exists."));

			} else {

				CustomResponse validateUpdateRequest = validate.updateLabourDepartment(requestObj);
				if (validateUpdateRequest.getStatus().equals(Responses.BAD_REQUEST.getCode())) {
					return validateUpdateRequest;
				}

				WorkorderLabourDepartment workorderLabourDepartment = setObject
						.workorderLabourDepartmentAddOrUpdateRequestDtoToEntity(requestObj);
				WorkorderLabourDepartment dbObj = workorderLabourDepartmentDao
						.fetchworkorderLabourDepartmentById(requestObj.getId());
				if (dbObj == null)
					return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Invalid id.");

				workorderLabourDepartment.setIsActive(dbObj.getIsActive());
				workorderLabourDepartment.setCreatedBy(dbObj.getCreatedBy());
				workorderLabourDepartment.setCreatedOn(dbObj.getCreatedOn());

				Boolean isUpdated = workorderLabourDepartmentDao
						.updateWorkorderLabourDepartment(workorderLabourDepartment);

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
	public CustomResponse getWorkorderLabourDepartmentList(Integer companyId) {
		try {

			List<WorkorderLabourDepartment> workorderLabourDepartment = workorderLabourDepartmentDao
					.fetchWorkorderLabourDepartmentDaoList(companyId);
			List<WorkorderLabourDepartmentFetchResponse> workorderLabourDepartmentResObj = new ArrayList<>();

			if (workorderLabourDepartment != null)
				workorderLabourDepartment.forEach(wld -> workorderLabourDepartmentResObj
						.add(setObject.workorderLabourDepartmentEntityToFetchResponseDto(wld)));

			PaginationResultObject resultObject = new PaginationResultObject(
					workorderLabourDepartment != null ? workorderLabourDepartment.size() : 0,
					workorderLabourDepartmentResObj);

			return new CustomResponse(Responses.SUCCESS.getCode(), resultObject, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	@Override
	public CustomResponse deactivateWorkorderLabourDepartment(Integer id, Long userId) {
		try {

			WorkorderLabourDepartment workorderLabourDepartment = workorderLabourDepartmentDao
					.fetchworkorderLabourDepartmentById(id);
			if (workorderLabourDepartment == null)
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Does not exist.");

			workorderLabourDepartment.setIsActive(false);
			workorderLabourDepartment.setModifiedOn(new Date());
			workorderLabourDepartment.setModifiedBy(userId.intValue());
			Boolean isDeactivated = workorderLabourDepartmentDao
					.deactivateWorkorderLabourDepartment(workorderLabourDepartment);

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
	public CustomResponse getWorkorderLabourDepartmentById(Integer id) {
		try {

			WorkorderLabourDepartment workorderLabourDepartment = workorderLabourDepartmentDao
					.fetchworkorderLabourDepartmentById(id);
			WorkorderLabourDepartmentFetchResponse resultObj = setObject
					.workorderLabourDepartmentEntityToFetchResponseDto(workorderLabourDepartment);

			return new CustomResponse(resultObj != null ? Responses.SUCCESS.getCode() : Responses.NOT_FOUND.getCode(),
					resultObj, resultObj != null ? Responses.SUCCESS.toString() : "Invalid id.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

}
