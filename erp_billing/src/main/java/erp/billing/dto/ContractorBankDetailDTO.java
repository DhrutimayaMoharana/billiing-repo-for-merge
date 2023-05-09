package erp.billing.dto;

import java.util.Date;

public class ContractorBankDetailDTO {

	private Long id;

	private Long contractorId;

	private String accountNo;

	private String accountName;

	private String bankName;

	private String ifscCode;

	private String address;

	private FileDTO cancelChequeFile;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private String cancelChequeFilePath;

	public ContractorBankDetailDTO() {
		super();
	}

	public ContractorBankDetailDTO(Long id) {
		super();
		this.id = id;
	}

	public ContractorBankDetailDTO(Long id, Long contractorId, String accountNo, String accountName, String bankName,
			String ifscCode, String address, FileDTO cancelChequeFile, Boolean isActive, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.contractorId = contractorId;
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.bankName = bankName;
		this.ifscCode = ifscCode;
		this.address = address;
		this.setCancelChequeFile(cancelChequeFile);
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCancelChequeFilePath() {
		return cancelChequeFilePath;
	}

	public void setCancelChequeFilePath(String cancelChequeFilePath) {
		this.cancelChequeFilePath = cancelChequeFilePath;
	}

	public FileDTO getCancelChequeFile() {
		return cancelChequeFile;
	}

	public void setCancelChequeFile(FileDTO cancelChequeFile) {
		this.cancelChequeFile = cancelChequeFile;
	}

}
