package erp.workorder.dao;

import erp.workorder.entity.EquipmentSummary;

public interface EquipmentSummaryDao {

	EquipmentSummary fetchEquipmentSummary(Byte machineType, Long machineId);

	Boolean saveEquipmentSummary(EquipmentSummary equipmentSummary);

	Boolean updateEquipmentSummary(EquipmentSummary equipmentSummary);

}
