package erp.billing.dao;

import java.util.List;

import erp.billing.entity.BillPayMilestones;

public interface BillPayMilestoneDao {

	Long saveBillPayMilestone(BillPayMilestones payMilestone);

	List<BillPayMilestones> fetchBillPayMilestones(Long billId);

	void forceUpdateBillPayMilestone(BillPayMilestones billPM);

	List<BillPayMilestones> fetchBillPayMilestonesByWorkorderId(Long id);

}
