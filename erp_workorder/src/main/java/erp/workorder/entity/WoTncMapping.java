package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "wo_tnc_mapping")
public class WoTncMapping implements Serializable {

	private static final long serialVersionUID = -1750634002616802583L;
	
	private Long id;
	
	private Workorder workorder;
	
	private WoTnc termAndCondition;
	
	private String description;
	
	private List<WoTncFormulaVariableValue> variableValues;
	
	private Integer sequenceNo;
	
	private Boolean isActive;
	
	private Date modifiedOn;
	
	private Long modifiedBy;

	public WoTncMapping() {
		super();
	}

	public WoTncMapping(Long id) {
		super();
		this.id = id;
	}


	public WoTncMapping(Long id, Workorder workorder, WoTnc termAndCondition, String description,
			List<WoTncFormulaVariableValue> variableValues, Integer sequenceNo, Boolean isActive, Date modifiedOn,
			Long modifiedBy) {
		super();
		this.id = id;
		this.workorder = workorder;
		this.termAndCondition = termAndCondition;
		this.description = description;
		this.setVariableValues(variableValues);
		this.setSequenceNo(sequenceNo);
		this.isActive = isActive;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workorder_id")
	@JsonIgnore
	public Workorder getWorkorder() {
		return workorder;
	}

	public void setWorkorder(Workorder workorder) {
		this.workorder = workorder;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "term_condition_id")
	public WoTnc getTermAndCondition() {
		return termAndCondition;
	}

	public void setTermAndCondition(WoTnc termAndCondition) {
		this.termAndCondition = termAndCondition;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "woTnc")
	public List<WoTncFormulaVariableValue> getVariableValues() {
		return variableValues;
	}

	public void setVariableValues(List<WoTncFormulaVariableValue> variableValues) {
		this.variableValues = variableValues;
	}

	@Column(name="is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name="modified_on")
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Column(name="modified_by")
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="sequence_no")
	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

}
