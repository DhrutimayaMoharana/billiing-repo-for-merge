package erp.workorder.service;

import java.util.List;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WorkorderListDTO;
import erp.workorder.dto.request.WorkorderBillInfoUpdateRequest;
import erp.workorder.dto.request.WorkorderRenewRequest;

public interface WorkorderService {

	CustomResponse getWorkorders(SearchDTO search);

	CustomResponse renderWorkorderById(SearchDTO search);

	CustomResponse getWorkorderMaterialEquipmentById(SearchDTO search);

	CustomResponse finishDraft(SearchDTO search);

	CustomResponse changeWorkorderState(SearchDTO search);

	CustomResponse getWorkorderNextStates(SearchDTO search);

	CustomResponse getSitesWorkorders(SearchDTO search);

	CustomResponse getFinalApprovedWorkorders(SearchDTO search);

	CustomResponse getWorkorderBillInfoByWorkorderId(Long workorderId);

	CustomResponse updateWorkorderBillInfo(WorkorderBillInfoUpdateRequest requestDTO);

	List<WorkorderListDTO> getWorkordersWithoutPagination(SearchDTO search);

	CustomResponse exportWorkOrderExcel(SearchDTO search);

	void triggerWorkorderBeforeExpiryMail();

	CustomResponse renewWorkorder(WorkorderRenewRequest requestDTO);

}
