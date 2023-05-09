package erp.billing.dto.request;

import java.util.Date;

public class WorkorderLabourAddUpdateRequest {

	private Integer id;

	private String code;

	private String name;

	private Integer departmentId;

	private Integer designationId;

	private Integer typeId;

	private Integer siteId;

	private Date fromDate;

	private Date toDate;

	private Integer userId;

	public WorkorderLabourAddUpdateRequest() {
		super();
	}

	public WorkorderLabourAddUpdateRequest(Integer id, String code, String name, Integer departmentId,
			Integer designationId, Integer typeId, Integer siteId, Date fromDate, Date toDate, Integer userId) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.departmentId = departmentId;
		this.designationId = designationId;
		this.typeId = typeId;
		this.siteId = siteId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Integer designationId) {
		this.designationId = designationId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
