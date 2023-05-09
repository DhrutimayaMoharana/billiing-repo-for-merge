package erp.workorder.dto.response;

public class MachineGetResponse {

	private Long id;

	private String assetCode;

	private String regNo;

	private Byte machineType;

	public MachineGetResponse() {
		super();
	}

	public MachineGetResponse(Long id, String assetCode, String regNo, Byte machineType) {
		super();
		this.id = id;
		this.assetCode = assetCode;
		this.regNo = regNo;
		this.machineType = machineType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public Byte getMachineType() {
		return machineType;
	}

	public void setMachineType(Byte machineType) {
		this.machineType = machineType;
	}

}
