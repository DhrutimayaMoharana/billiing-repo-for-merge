package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.Equipment;
import erp.workorder.entity.EquipmentCategory;
import erp.workorder.entity.WorkorderEquipmentIssue;
import erp.workorder.entity.WorkorderEquipmentIssueVersion;

public interface WorkorderEquipmentIssueDao {

	List<Equipment> fetchEquipments(SearchDTO search);

	List<Equipment> fetchEquipmentsByWorkorderId(Long workorderId);

	Long issueEquipment(WorkorderEquipmentIssue woEquipmentIssue);

	List<WorkorderEquipmentIssue> fetchWorkorderIssuedEquipments(SearchDTO search);

	List<WorkorderEquipmentIssue> fetchWorkorderIssuedEquipmentsByWorkorderId(Long workorderId);

	List<EquipmentCategory> fetchEquipmentCategories(SearchDTO search);

	Boolean updateIssuedEquipment(WorkorderEquipmentIssue issuedEquipment);

	WorkorderEquipmentIssue fetchIssuedEquipmentById(Long id);

	List<Equipment> fetchHiredEquipments(Long siteId);

	List<Equipment> fetchHiredEquipments(Long siteId, Long categoryId);

	Long saveWorkorderEquipmentIssueVersion(WorkorderEquipmentIssueVersion woEquipmentIssueVersion);

}
