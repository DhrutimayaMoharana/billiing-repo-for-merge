package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.WorkorderLabourWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderLabourWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderLabourWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderLabourWorkItemDeactivateRequest;

public interface WorkorderLabourWorkService {

	CustomResponse addOrUpdateWorkorderLabourWork(WorkorderLabourWorkAddUpdateRequestDTO labourWorkDTO);

	CustomResponse addOrUpdateWorkorderLabourWorkItem(WorkorderLabourWorkItemAddUpdateRequest labourWorkItemDTO);

	CustomResponse deactivateWorkorderLabourWorkItem(WorkorderLabourWorkItemDeactivateRequest labourWorkItemDTO);

	CustomResponse getWorkorderLabourWork(WorkorderLabourWorkGetRequestDTO labourWorkDTO);

	CustomResponse amendWorkorderLabourWorkFlow(UserDetail userDetail, Long amendWorkorderId, Long workorderId);

}
