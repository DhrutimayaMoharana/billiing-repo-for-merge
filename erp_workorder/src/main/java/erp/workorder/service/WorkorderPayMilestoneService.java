package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.request.WorkorderPayMilestoneAddUpdateRequest;
import erp.workorder.dto.request.WorkorderPayMilestoneDeactivateRequest;
import erp.workorder.dto.request.WorkorderPayMilestoneGetRequest;

public interface WorkorderPayMilestoneService {

	CustomResponse getWorkorderWorkorderPayMilestones(WorkorderPayMilestoneGetRequest payTermReq);

	CustomResponse addUpdateWorkorderWorkorderPayMilestone(WorkorderPayMilestoneAddUpdateRequest payTermReq);

	CustomResponse deactivateWorkorderWorkorderPayMilestone(WorkorderPayMilestoneDeactivateRequest payTermReq);

}
