package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WorkorderEquipmentIssueDTO;

public interface WorkorderEquipmentIssueService {

	CustomResponse getEquipments(SearchDTO search);

	CustomResponse issueEquipment(WorkorderEquipmentIssueDTO woEquipmentIssue);

	CustomResponse getEquipmentCategories(SearchDTO search);

	CustomResponse getIssueCostPeriods(SearchDTO search);

	CustomResponse deactivateIssuedEquipment(SearchDTO search);

	CustomResponse updateIssuedEquipment(WorkorderEquipmentIssueDTO woEquipmentIssue);

	CustomResponse getCategoryMachines(Long siteId);

	CustomResponse getHiredMachines(Long siteId, Long categoryId);

}
