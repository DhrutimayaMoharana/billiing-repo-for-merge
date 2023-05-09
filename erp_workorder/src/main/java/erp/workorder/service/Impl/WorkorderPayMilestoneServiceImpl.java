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
import erp.workorder.dao.WorkorderPayMilestoneDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.request.WorkorderPayMilestoneAddUpdateRequest;
import erp.workorder.dto.request.WorkorderPayMilestoneDeactivateRequest;
import erp.workorder.dto.request.WorkorderPayMilestoneGetRequest;
import erp.workorder.dto.response.WorkorderPayMilestoneGetResponse;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderPayMilestone;
import erp.workorder.enums.Responses;
import erp.workorder.enums.WorkorderTypes;
import erp.workorder.service.WorkorderPayMilestoneService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class WorkorderPayMilestoneServiceImpl implements WorkorderPayMilestoneService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkorderPayMilestoneDao payTermDao;

	@Autowired
	private CustomValidationUtil validationUtil;

	@Autowired
	private SetObject setObject;

	@Autowired
	private WorkorderDao workorderDao;

	@Override
	public CustomResponse getWorkorderWorkorderPayMilestones(WorkorderPayMilestoneGetRequest payTermReq) {

		try {
			CustomResponse validationRes = validationUtil.validateGetWorkorderWorkorderPayMilestones(payTermReq);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			Workorder workorder = workorderDao.fetchWorkorderById(payTermReq.getWorkorderId());
			if (workorder == null)
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid workorderId.");

			List<WorkorderPayMilestone> payMilestones = payTermDao
					.fetchWorkorderWorkorderPayMilestones(workorder.getId());
			List<WorkorderPayMilestoneGetResponse> resultResponse = new ArrayList<>();
			if (payMilestones != null) {
				for (WorkorderPayMilestone obj : payMilestones) {
					resultResponse.add(new WorkorderPayMilestoneGetResponse(obj.getId(), obj.getDescription(),
							obj.getIsPercentage(), obj.getValue()));
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), resultResponse, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addUpdateWorkorderWorkorderPayMilestone(WorkorderPayMilestoneAddUpdateRequest payTermReq) {

		try {
			CustomResponse validationRes = validationUtil.validateAddUpdateWorkorderWorkorderPayMilestone(payTermReq);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}

			Workorder workorder = workorderDao.fetchWorkorderById(payTermReq.getWorkorderId());
			if (workorder == null)
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid workorderId.");
			if (workorder.getType().getId().equals(WorkorderTypes.Consultancy.getId())) {

				if (payTermReq.getId() == null) {

					WorkorderPayMilestone payMilestone = setObject.workorderPayMilestoneAddRequestToEntity(payTermReq);
					Long id = payTermDao.saveWorkorderWorkorderPayMilestone(payMilestone);
					return new CustomResponse(Responses.SUCCESS.getCode(), null,
							id != null ? "Added." : "Already exists.");

				} else {

					WorkorderPayMilestone payMilestone = payTermDao
							.fetchWorkorderWorkorderPayMilestoneById(payTermReq.getId());

					payMilestone = setObject.updatedWorkorderPayMilestone(payTermReq, payMilestone);
					Boolean isUpdated = false;
					if (payMilestone != null) {
						isUpdated = payTermDao.updateWorkorderPayMilestone(payMilestone);
					}
					return new CustomResponse(Responses.SUCCESS.getCode(), null,
							isUpdated ? "Updated." : "Unable to update.");
				}

			}

			return new CustomResponse(Responses.FORBIDDEN.getCode(), null, Responses.FORBIDDEN.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateWorkorderWorkorderPayMilestone(WorkorderPayMilestoneDeactivateRequest payTermReq) {

		try {
			CustomResponse validationRes = validationUtil.validateDeactivateWorkorderWorkorderPayMilestone(payTermReq);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			WorkorderPayMilestone payMilestone = payTermDao
					.fetchWorkorderWorkorderPayMilestoneById(payTermReq.getWorkorderPayMilestoneId());

			if (payMilestone != null) {
				payMilestone.setIsActive(false);
				payMilestone.setModifiedOn(new Date());
				payMilestone.setModifiedBy(payTermReq.getTokenDetails().getId());
				payTermDao.forceUpdateWorkorderPayMilestone(payMilestone);
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), null, "Removed.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
