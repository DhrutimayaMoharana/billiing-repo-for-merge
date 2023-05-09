package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.WorkorderConsultantWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderConsultantWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderConsultantWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderConsultantWorkItemDeactivateRequestDTO;

public interface WorkorderConsultantWorkService {

	CustomResponse addOrUpdateWorkorderConsultantWork(WorkorderConsultantWorkAddUpdateRequestDTO consultantWork);

	CustomResponse getWorkorderConsultantWork(WorkorderConsultantWorkGetRequestDTO consultantWorkDTO);

	CustomResponse addOrUpdateWorkorderConsultantWorkItem(
			WorkorderConsultantWorkItemAddUpdateRequest consultantWorkItemDTO);

	CustomResponse deactivateWorkorderConsultantWorkItem(
			WorkorderConsultantWorkItemDeactivateRequestDTO consultantWorkItemDTO);

	CustomResponse getWorkorderConsultantWorkItemCategoryDescriptions(Integer companyId);

	CustomResponse amendWorkorderConsultantWorkFlow(UserDetail userDetail, Long amendWorkorderId, Long workorderId);

}
