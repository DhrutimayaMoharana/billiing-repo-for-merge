package erp.boq_mgmt.dto.request;

import java.util.Date;

import erp.boq_mgmt.enums.RfiMainCommentType;

public class RfiMainCommentAddUpdateRequest {

	private Long id;

	private RfiMainCommentType commentType;

	private String commentatorName;

	private Date commentTimestamp;

	private String comment;

	public RfiMainCommentAddUpdateRequest() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RfiMainCommentType getCommentType() {
		return commentType;
	}

	public void setCommentType(RfiMainCommentType commentType) {
		this.commentType = commentType;
	}

	public String getCommentatorName() {
		return commentatorName;
	}

	public void setCommentatorName(String commentatorName) {
		this.commentatorName = commentatorName;
	}

	public Date getCommentTimestamp() {
		return commentTimestamp;
	}

	public void setCommentTimestamp(Date commentTimestamp) {
		this.commentTimestamp = commentTimestamp;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
