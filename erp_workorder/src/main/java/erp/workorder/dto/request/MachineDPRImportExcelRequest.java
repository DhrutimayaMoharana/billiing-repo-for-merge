package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class MachineDPRImportExcelRequest {

	private Byte machineType;

	private Long machineId;

	private Long siteId;

	private UserDetail userDetail;

	public MachineDPRImportExcelRequest() {
		super();
	}

	public MachineDPRImportExcelRequest(Byte machineType, Long machineId, Long siteId, UserDetail userDetail) {
		super();
		this.machineType = machineType;
		this.machineId = machineId;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Byte getMachineType() {
		return machineType;
	}

	public void setMachineType(Byte machineType) {
		this.machineType = machineType;
	}

	public Long getMachineId() {
		return machineId;
	}

	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
