package erp.billing.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import erp.billing.entity.MachineDPR;

public interface DprHireMachineDao {

	List<MachineDPR> fetchMachineDprList(Set<Long> machineIds, Date fromDate, Date toDate, Long siteId);

}
