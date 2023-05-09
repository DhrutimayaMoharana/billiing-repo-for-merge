package erp.billing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "client_invoice_state_transition_mapping")
public class ClientInvoiceStateTransitionMappingV2 implements Serializable {

	private static final long serialVersionUID = -2198976131199873703L;

	private Long id;

	private Long clientInvoiceId;

	private EngineState state;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private User createdByUser;

	private ClientInvoice clientInvoice;

	public ClientInvoiceStateTransitionMappingV2() {
		super();
	}

	public ClientInvoiceStateTransitionMappingV2(Long id, Long clientInvoiceId, EngineState state, Boolean isActive,
			Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.clientInvoiceId = clientInvoiceId;
		this.state = state;
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

	@OneToOne
	@JoinColumn(name = "state_id")
	public EngineState getState() {
		return state;
	}

	public void setState(EngineState state) {
		this.state = state;
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

	@OneToOne
	@JoinColumn(name = "created_by", insertable = false, updatable = false)
	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

	@OneToOne
	@JoinColumn(name = "client_invoice_id", insertable = false, updatable = false)
	public ClientInvoice getClientInvoice() {
		return clientInvoice;
	}

	public void setClientInvoice(ClientInvoice clientInvoice) {
		this.clientInvoice = clientInvoice;
	}

}