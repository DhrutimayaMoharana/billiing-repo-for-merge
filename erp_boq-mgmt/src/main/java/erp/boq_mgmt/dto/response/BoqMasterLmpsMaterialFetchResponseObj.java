package erp.boq_mgmt.dto.response;

import java.util.List;

public class BoqMasterLmpsMaterialFetchResponseObj {

	private List<BoqMasterLmpsMaterialResponse> materialList;

	public BoqMasterLmpsMaterialFetchResponseObj() {
		super();
	}

	public BoqMasterLmpsMaterialFetchResponseObj(List<BoqMasterLmpsMaterialResponse> materialList) {
		super();
		this.materialList = materialList;
	}

	public List<BoqMasterLmpsMaterialResponse> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<BoqMasterLmpsMaterialResponse> materialList) {
		this.materialList = materialList;
	}

}
