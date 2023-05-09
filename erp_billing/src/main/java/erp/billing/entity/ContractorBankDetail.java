package erp.billing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contractor_bank_details")
public class ContractorBankDetail implements Serializable {

	private static final long serialVersionUID = -4004495071855239996L;

	private Long id;

	private Long contractorId;

	private String accountNo;

	private String accountName;

	private String bankName;

	private String ifscCode;

	private String address;

	private FileEntity cancelChequeFile;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public ContractorBankDetail() {
		super();
	}

	public ContractorBankDetail(Long id) {
		super();
		this.id = id;
	}

	public ContractorBankDetail(Long id, Long contractorId, String accountNo, String accountName, String bankName,
			String ifscCode, String address, FileEntity cancelChequeFile, Boolean isActive, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.contractorId = contractorId;
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.bankName = bankName;
		this.ifscCode = ifscCode;
		this.address = address;
		this.cancelChequeFile = cancelChequeFile;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "contractor_id")
	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	@Column(name = "acct_no")
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Column(name = "acct_name")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "bank_name")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "ifsc_code")
	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cancel_cheque_file_id")
	public FileEntity getCancelChequeFile() {
		return cancelChequeFile;
	}

	public void setCancelChequeFile(FileEntity cancelChequeFile) {
		this.cancelChequeFile = cancelChequeFile;
	}

}
