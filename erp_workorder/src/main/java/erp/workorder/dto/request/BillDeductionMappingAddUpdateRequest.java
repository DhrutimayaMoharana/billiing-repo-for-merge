package erp.workorder.dto.request;

import java.util.List;

import erp.workorder.dto.UserDetail;

public class BillDeductionMappingAddUpdateRequest {

	private Integer workorderBillInfoId;

	private List<BillDeductionMappingDTO> billDeductionMapRequest;

	private UserDetail userDetail;

	public BillDeductionMappingAddUpdateRequest() {
		super();
	}

	public BillDeductionMappingAddUpdateRequest(Integer workorderBillInfoId,
			List<BillDeductionMappingDTO> billDeductionMapRequest, UserDetail userDetail) {
		super();
		this.workorderBillInfoId = workorderBillInfoId;
		this.billDeductionMapRequest = billDeductionMapRequest;
		this.userDetail = userDetail;
	}

	public Integer getWorkorderBillInfoId() {
		return workorderBillInfoId;
	}

	public void setWorkorderBillInfoId(Integer workorderBillInfoId) {
		this.workorderBillInfoId = workorderBillInfoId;
	}

	public List<BillDeductionMappingDTO> getBillDeductionMapRequest() {
		return billDeductionMapRequest;
	}

	public void setBillDeductionMapRequest(List<BillDeductionMappingDTO> billDeductionMapRequest) {
		this.billDeductionMapRequest = billDeductionMapRequest;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
