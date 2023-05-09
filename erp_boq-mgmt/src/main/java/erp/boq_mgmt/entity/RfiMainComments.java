package erp.boq_mgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import erp.boq_mgmt.enums.RfiMainCommentType;

@Entity
@Table(name = "rfi_main_comments")
public class RfiMainComments implements Serializable {

	private static final long serialVersionUID = 2572817587342396674L;

	private Long id;

	private Long rfiMainId;

	private RfiMainCommentType commentType;

	private String commentatorName;

	private Date commentTimestamp;

	private String comment;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	public RfiMainComments() {
		super();
	}

	public RfiMainComments(Long id, Long rfiMainId, RfiMainCommentType commentType, String commentatorName,
			Date commentTimestamp, String comment, Boolean isActive, Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.rfiMainId = rfiMainId;
		this.commentType = commentType;
		this.commentatorName = commentatorName;
		this.commentTimestamp = commentTimestamp;
		this.comment = comment;
		this.isActive = isActive;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
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

	@Column(name = "rfi_main_id")
	public Long getRfiMainId() {
		return rfiMainId;
	}

	public void setRfiMainId(Long rfiMainId) {
		this.rfiMainId = rfiMainId;
	}

	@Column(name = "comment_type")
	public RfiMainCommentType getCommentType() {
		return commentType;
	}

	public void setCommentType(RfiMainCommentType commentType) {
		this.commentType = commentType;
	}

	@Column(name = "commentator_name")
	public String getCommentatorName() {
		return commentatorName;
	}

	public void setCommentatorName(String commentatorName) {
		this.commentatorName = commentatorName;
	}

	@Column(name = "comment_timestamp")
	public Date getCommentTimestamp() {
		return commentTimestamp;
	}

	public void setCommentTimestamp(Date commentTimestamp) {
		this.commentTimestamp = commentTimestamp;
	}

	@Column(name = "comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

}
