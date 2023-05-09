package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.WorkorderHiringMachineWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderHiringMachineWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderHiringMachineWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderHiringMachineWorkItemDeactivateRequestDTO;

public interface WorkorderHiringMachineWorkService {

	CustomResponse addOrUpdateWorkorderHiringMachineWork(WorkorderHiringMachineWorkAddUpdateRequestDTO hmWork);

	CustomResponse getWorkorderHiringMachineWork(WorkorderHiringMachineWorkGetRequestDTO hmWorkDTO);

	CustomResponse addOrUpdateWorkorderHiringMachineWorkItem(
			WorkorderHiringMachineWorkItemAddUpdateRequest hmWorkItemDTO);

	CustomResponse deactivateWorkorderHiringMachineWorkItem(
			WorkorderHiringMachineWorkItemDeactivateRequestDTO hmWorkItemDTO);

	CustomResponse getWorkorderHiringMachineUnits();

	CustomResponse amendWorkorderHiringMachineWorkFlow(UserDetail userDetail, Long amendWorkorderId, Long workorderId);

}
