package erp.boq_mgmt.dto.response;

import java.util.Date;
import java.util.Map;

public class ProjectPlanBoqResponse {

	private Long id;

	private Integer categoryId;

	private String categoryName;

	private String standardBoqCode;

	private String systemCode;

	private String name;

	private Long structureTypeId;

	private String structureTypeName;

	private Integer unitId;

	private String unitName;

	private Date startDate;

	private Date endDate;

	private Double totalQuantity;

	private Map<Date, Double> monthlyAllocatedQuantity;

	public ProjectPlanBoqResponse() {
		super();
	}

	public ProjectPlanBoqResponse(Long id, Integer categoryId, String categoryName, String standardBoqCode,
			String systemCode, String name, Long structureTypeId, String structureTypeName, Integer unitId,
			String unitName, Date startDate, Date endDate, Double totalQuantity,
			Map<Date, Double> monthlyAllocatedQuantity) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.standardBoqCode = standardBoqCode;
		this.systemCode = systemCode;
		this.name = name;
		this.structureTypeId = structureTypeId;
		this.structureTypeName = structureTypeName;
		this.unitId = unitId;
		this.unitName = unitName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalQuantity = totalQuantity;
		this.monthlyAllocatedQuantity = monthlyAllocatedQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getStandardBoqCode() {
		return standardBoqCode;
	}

	public void setStandardBoqCode(String standardBoqCode) {
		this.standardBoqCode = standardBoqCode;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStructureTypeId() {
		return structureTypeId;
	}

	public void setStructureTypeId(Long structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

	public String getStructureTypeName() {
		return structureTypeName;
	}

	public void setStructureTypeName(String structureTypeName) {
		this.structureTypeName = structureTypeName;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Map<Date, Double> getMonthlyAllocatedQuantity() {
		return monthlyAllocatedQuantity;
	}

	public void setMonthlyAllocatedQuantity(Map<Date, Double> monthlyAllocatedQuantity) {
		this.monthlyAllocatedQuantity = monthlyAllocatedQuantity;
	}

}
