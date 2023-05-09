package erp.billing.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import erp.billing.entity.IssueSlipItem;

public interface IssueSlipItemDao {

	List<IssueSlipItem> fetchIssueSlipItems(Date fromDate, Date toDate, Set<Long> machineIds);

}
