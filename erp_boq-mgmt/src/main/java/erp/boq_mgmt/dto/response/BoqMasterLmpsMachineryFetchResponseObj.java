package erp.boq_mgmt.dto.response;

import java.util.List;

public class BoqMasterLmpsMachineryFetchResponseObj {

	private List<BoqMasterLmpsMachineryResponse> machineryList;

	public BoqMasterLmpsMachineryFetchResponseObj() {
		super();
	}

	public BoqMasterLmpsMachineryFetchResponseObj(List<BoqMasterLmpsMachineryResponse> machineryList) {
		super();
		this.machineryList = machineryList;
	}

	public List<BoqMasterLmpsMachineryResponse> getMachineryList() {
		return machineryList;
	}

	public void setMachineryList(List<BoqMasterLmpsMachineryResponse> machineryList) {
		this.machineryList = machineryList;
	}

}
