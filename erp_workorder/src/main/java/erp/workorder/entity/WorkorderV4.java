
package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import erp.workorder.enums.WorkorderCloseType;

@Entity
@Table(name = "billing_workorder")
public class WorkorderV4 implements Serializable {

	private static final long serialVersionUID = 1962731406097731714L;

	private Long id;

	private Date endDate;

	private WorkorderCloseType closeType;

	private Date modifiedOn;

	private Long modifiedBy;

	public WorkorderV4() {
		super();
	}

	public WorkorderV4(Long id, Date endDate, WorkorderCloseType closeType, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.endDate = endDate;
		this.closeType = closeType;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "close_type")
	public WorkorderCloseType getCloseType() {
		return closeType;
	}

	public void setCloseType(WorkorderCloseType closeType) {
		this.closeType = closeType;
	}

	@Column(name = "modified_on")
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Column(name = "modified_by")
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
