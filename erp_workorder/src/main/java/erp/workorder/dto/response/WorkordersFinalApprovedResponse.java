package erp.workorder.dto.response;

import java.util.Date;

public class WorkordersFinalApprovedResponse {

	private Long workorderId;

	private String subject;

	private String uniqueNo;

	private Date startDate;

	private String contractorName;

	private String typeName;

	private String stateName;

	private Integer version;

	public WorkordersFinalApprovedResponse() {
		super();
	}

	public WorkordersFinalApprovedResponse(Long workorderId, String subject, String uniqueNo, Date startDate,
			String contractorName, String typeName, String stateName, Integer version) {
		super();
		this.workorderId = workorderId;
		this.subject = subject;
		this.uniqueNo = uniqueNo;
		this.startDate = startDate;
		this.contractorName = contractorName;
		this.typeName = typeName;
		this.stateName = stateName;
		this.version = version;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUniqueNo() {
		return uniqueNo;
	}

	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
