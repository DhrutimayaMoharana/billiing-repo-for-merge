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

import erp.billing.dao.WorkorderLabourDesignationDao;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.request.WorkorderLabourDesignationAddUpdateRequest;
import erp.billing.dto.response.PaginationResultObject;
import erp.billing.dto.response.WorkorderLabourDesignationFetchResponse;
import erp.billing.entity.WorkorderLabourDesignation;
import erp.billing.enums.Responses;
import erp.billing.service.WorkorderLabourDesignationService;
import erp.billing.util.CustomValidationUtil;
import erp.billing.util.SetObject;

@Transactional(rollbackFor = Exception.class)
@Service
public class WorkorderLabourDesignationServiceImpl implements WorkorderLabourDesignationService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private WorkorderLabourDesignationDao workorderLabourDesignationDao;
	@Autowired
	private CustomValidationUtil validate;

	@Override
	public CustomResponse addOrUpdateWorkorderLabourDesignation(WorkorderLabourDesignationAddUpdateRequest requestObj) {
		try {

			if (requestObj.getId() == null) {

				CustomResponse validateAddRequest = validate.addLabourDesignation(requestObj);
				if (validateAddRequest.getStatus().equals(Responses.BAD_REQUEST.getCode())) {
					return validateAddRequest;
				}

				WorkorderLabourDesignation workorderLabourDesignation = setObject
						.workorderLabourDesignationAddOrUpdateRequestDtoToEntity(requestObj);

				Integer id = workorderLabourDesignationDao.saveWorkorderLabourDesignation(workorderLabourDesignation);

				return new CustomResponse(
						((id != null && id.longValue() > 0) ? Responses.SUCCESS.getCode()
								: Responses.FORBIDDEN.getCode()),
						id, ((id != null && id.longValue() > 0) ? Responses.SUCCESS.toString() : "Already exists."));

			} else {

				CustomResponse validateUpdateRequest = validate.updateLabourDesignation(requestObj);
				if (validateUpdateRequest.getStatus().equals(Responses.BAD_REQUEST.getCode())) {
					return validateUpdateRequest;
				}

				WorkorderLabourDesignation workorderLabourDesignation = setObject
						.workorderLabourDesignationAddOrUpdateRequestDtoToEntity(requestObj);
				WorkorderLabourDesignation dbObj = workorderLabourDesignationDao
						.fetchworkorderLabourDesignationById(requestObj.getId());
				if (dbObj == null)
					return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Invalid id.");

				workorderLabourDesignation.setIsActive(dbObj.getIsActive());
				workorderLabourDesignation.setCreatedBy(dbObj.getCreatedBy());
				workorderLabourDesignation.setCreatedOn(dbObj.getCreatedOn());

				Boolean isUpdated = workorderLabourDesignationDao
						.updateWorkorderLabourDesignation(workorderLabourDesignation);

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
	public CustomResponse getWorkorderLabourDesignationList(Integer companyId) {
		try {

			List<WorkorderLabourDesignation> workorderLabourDesignation = workorderLabourDesignationDao
					.fetchWorkorderLabourDesignationDaoList(companyId);
			List<WorkorderLabourDesignationFetchResponse> workorderLabourDesignationResObj = new ArrayList<>();

			if (workorderLabourDesignation != null)
				workorderLabourDesignation.forEach(wld -> workorderLabourDesignationResObj
						.add(setObject.workorderLabourDesignationEntityToFetchResponseDto(wld)));

			PaginationResultObject resultObject = new PaginationResultObject(
					workorderLabourDesignation != null ? workorderLabourDesignation.size() : 0,
					workorderLabourDesignationResObj);

			return new CustomResponse(Responses.SUCCESS.getCode(), resultObject, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	@Override
	public CustomResponse deactivateWorkorderLabourDesignation(Integer id, Long userId) {
		try {

			WorkorderLabourDesignation workorderLabourDesignation = workorderLabourDesignationDao
					.fetchworkorderLabourDesignationById(id);
			if (workorderLabourDesignation == null)
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Does not exist.");

			workorderLabourDesignation.setIsActive(false);
			workorderLabourDesignation.setModifiedOn(new Date());
			workorderLabourDesignation.setModifiedBy(userId.intValue());
			Boolean isDeactivated = workorderLabourDesignationDao
					.deactivateWorkorderLabourDesignation(workorderLabourDesignation);

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
	public CustomResponse getWorkorderLabourDesignationById(Integer id) {
		try {

			WorkorderLabourDesignation workorderLabourDesignation = workorderLabourDesignationDao
					.fetchworkorderLabourDesignationById(id);
			WorkorderLabourDesignationFetchResponse resultObj = setObject
					.workorderLabourDesignationEntityToFetchResponseDto(workorderLabourDesignation);

			return new CustomResponse(resultObj != null ? Responses.SUCCESS.getCode() : Responses.NOT_FOUND.getCode(),
					resultObj, resultObj != null ? Responses.SUCCESS.toString() : "Invalid id.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

}
