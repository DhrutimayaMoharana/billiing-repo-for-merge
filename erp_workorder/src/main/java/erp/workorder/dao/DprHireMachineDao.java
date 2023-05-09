package erp.workorder.dao;

import java.util.Date;
import java.util.List;

import erp.workorder.entity.Equipment;
import erp.workorder.entity.MachineDPR;

public interface DprHireMachineDao {

	MachineDPR fetchMachineDprById(Long id);

	Boolean updateMachineDpr(MachineDPR mDpr);

	Boolean mergeAndUpdateMachineDpr(MachineDPR mDpr);

	Long saveMachineDpr(MachineDPR mDpr);

	void forceUpdateMachineDpr(MachineDPR mDpr);

	List<MachineDPR> fetchMachineDprList(Long machineId, Byte machineType, Date fromDate, Date toDate, Long siteId);

	Equipment fetchEquipmentById(Long machineId);

	MachineDPR fetchPreviousDateMachineDprByDate(Date dated, Byte machineType, Long machineId, Long siteId);

	MachineDPR fetchNextDateMachineDprByDate(Date dated, Byte machineType, Long machineId, Long siteId);

	List<MachineDPR> fetchMachineDprListTillCurrentDate(Long machineId, Byte machineType);

	List<MachineDPR> fetchSameDateMachineDprByDate(Date dated, Byte machineType, Long machineId, Long siteId);

}
