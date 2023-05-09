package erp.workorder.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import erp.workorder.entity.IssueSlipItem;

public interface IssueSlipItemDao {

	List<IssueSlipItem> fetchIssueSlipItems(Date fromDate, Date toDate, Set<Long> machineIds);

}
