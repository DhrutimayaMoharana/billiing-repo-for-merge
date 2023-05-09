package erp.billing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client_invoice_state_transition_mapping")
public class ClientInvoiceStateTransitionMapping implements Serializable {

	private static final long serialVersionUID = -2198976131199873703L;

	private Long id;

	private Long clientInvoiceId;

	private Integer stateId;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public ClientInvoiceStateTransitionMapping() {
		super();
	}

	public ClientInvoiceStateTransitionMapping(Long id, Long clientInvoiceId, Integer stateId, Boolean isActive,
			Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.clientInvoiceId = clientInvoiceId;
		this.stateId = stateId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "client_invoice_id")
	public Long getClientInvoiceId() {
		return clientInvoiceId;
	}

	public void setClientInvoiceId(Long clientInvoiceId) {
		this.clientInvoiceId = clientInvoiceId;
	}

	@Column(name = "state_id")
	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
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
}