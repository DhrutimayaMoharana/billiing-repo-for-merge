package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.WorkorderTransportationWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderTransportationWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderTransportationWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderTransportationWorkItemDeactivateRequestDTO;

public interface WorkorderTransportationWorkService {

	CustomResponse addOrUpdateWorkorderTransportationWork(
			WorkorderTransportationWorkAddUpdateRequestDTO transportWorkDTO);

	CustomResponse addOrUpdateWorkorderTransportationWorkItem(
			WorkorderTransportationWorkItemAddUpdateRequest trasportWorkItemDTO);

	CustomResponse deactivateWorkorderTransportationWorkItem(
			WorkorderTransportationWorkItemDeactivateRequestDTO transportWorkItemDTO);

	CustomResponse getWorkorderTransportationWork(WorkorderTransportationWorkGetRequestDTO transportWorkDTO);

	CustomResponse getWorkorderTransportationBoqDescriptions(Integer companyId);

	CustomResponse amendWorkorderTransportWorkFlow(UserDetail userDetail, Long amendWorkorderId, Long workorderId);

}
