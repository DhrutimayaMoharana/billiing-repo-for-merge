package erp.billing.dao;

import erp.billing.entity.Equipment;

public interface MachineDao {

	Equipment fetchEquipment(Long equipmentId);

}
