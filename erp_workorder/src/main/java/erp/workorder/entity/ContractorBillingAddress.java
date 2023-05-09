package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contractor_billing_address")
public class ContractorBillingAddress implements Serializable {

	private static final long serialVersionUID = -5837010926528972258L;
	
	private Long id;
	
	private Long contractorId;
	
	private String address;
	
	private String gstNo;
	
	private Boolean isActive;
	
	private Date createdOn;
	
	private Long createdBy;

	public ContractorBillingAddress() {
		super();
	}

	public ContractorBillingAddress(Long id) {
		super();
		this.id = id;
	}

	public ContractorBillingAddress(Long id, Long contractorId, String address, String gstNo, Boolean isActive, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.contractorId = contractorId;
		this.address = address;
		this.gstNo = gstNo;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="contractor_id")
	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	@Column(name="address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name="created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="gst_no")
	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

}
