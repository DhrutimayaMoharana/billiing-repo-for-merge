package erp.billing.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "debit_note_tax")
public class DebitNoteTax implements Serializable {

	private static final long serialVersionUID = 1L;

	private Short id;

	private Double taxPercentage;

	private Boolean isActive;

	public DebitNoteTax() {
		super();
	}

	public DebitNoteTax(Short id, Double taxPercentage, Boolean isActive) {
		super();
		this.id = id;
		this.taxPercentage = taxPercentage;
		this.isActive = isActive;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	@Column(name = "tax_percentage")
	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
