package erp.boq_mgmt.dto.response;

import java.util.List;

public class BoqMasterLmpsLabourFetchResponseObj {

	private List<BoqMasterLmpsLabourResponse> labourList;

	public BoqMasterLmpsLabourFetchResponseObj() {
		super();
	}

	public BoqMasterLmpsLabourFetchResponseObj(List<BoqMasterLmpsLabourResponse> labourList) {
		super();
		this.labourList = labourList;
	}

	public List<BoqMasterLmpsLabourResponse> getLabourList() {
		return labourList;
	}

	public void setLabourList(List<BoqMasterLmpsLabourResponse> labourList) {
		this.labourList = labourList;
	}

}
