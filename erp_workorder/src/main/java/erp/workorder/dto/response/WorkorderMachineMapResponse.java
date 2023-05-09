package erp.workorder.dto.response;

public class WorkorderMachineMapResponse {

	private Long id;

	private Long machineId;

	private Byte machineType;

	private String assetCode;

	private String regNo;

	private Double amount;

	public WorkorderMachineMapResponse() {
		super();

	}

	public WorkorderMachineMapResponse(Long id, Long machineId, Byte machineType, String assetCode, String regNo,
			Double amount) {
		super();
		this.id = id;
		this.machineId = machineId;
		this.machineType = machineType;
		this.assetCode = assetCode;
		this.regNo = regNo;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMachineId() {
		return machineId;
	}

	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}

	public Byte getMachineType() {
		return machineType;
	}

	public void setMachineType(Byte machineType) {
		this.machineType = machineType;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
