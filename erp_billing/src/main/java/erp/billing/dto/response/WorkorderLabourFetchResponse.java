package erp.billing.dto.response;

import java.util.Date;

public class WorkorderLabourFetchResponse {

	private Integer id;

	private String code;

	private String name;

	private Integer departmentId;

	private String departmentName;

	private Integer designationId;

	private String designationName;

	private Integer typeId;

	private String typeName;

	private Date fromDate;

	private Date toDate;

	public WorkorderLabourFetchResponse() {
		super();
	}

	public WorkorderLabourFetchResponse(Integer id, String code, String name, Integer departmentId,
			String departmentName, Integer designationId, String designationName, Integer typeId, String typeName,
			Date fromDate, Date toDate) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.designationId = designationId;
		this.designationName = designationName;
		this.typeId = typeId;
		this.typeName = typeName;
		this.fromDate = fromDate;
		this.toDate = toDate;
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Integer designationId) {
		this.designationId = designationId;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

}
