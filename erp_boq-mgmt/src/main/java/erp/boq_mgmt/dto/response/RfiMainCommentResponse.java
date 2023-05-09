package erp.boq_mgmt.dto.response;

import java.util.Date;

import erp.boq_mgmt.enums.RfiMainCommentType;

public class RfiMainCommentResponse {

	private Long id;

	private RfiMainCommentType commentType;

	private String commentatorName;

	private Date commentTimestamp;

	private String comment;

	public RfiMainCommentResponse() {
		super();
	}

	public RfiMainCommentResponse(Long id, RfiMainCommentType commentType, String commentatorName,
			Date commentTimestamp, String comment) {
		super();
		this.id = id;
		this.commentType = commentType;
		this.commentatorName = commentatorName;
		this.commentTimestamp = commentTimestamp;
		this.comment = comment;
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
