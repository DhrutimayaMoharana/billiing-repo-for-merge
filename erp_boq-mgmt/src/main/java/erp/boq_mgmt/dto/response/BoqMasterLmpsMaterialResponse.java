package erp.boq_mgmt.dto.response;

public class BoqMasterLmpsMaterialResponse {

	private Long id;

	private Long boqMasterLmpsId;

	private Integer materialId;

	private String materialName;

	private Integer unitId;

	private String unitName;

	private Double quantity;

	public BoqMasterLmpsMaterialResponse() {
		super();
	}

	public BoqMasterLmpsMaterialResponse(Long id, Long boqMasterLmpsId, Integer materialId, String materialName,
			Integer unitId, String unitName, Double quantity) {
		super();
		this.id = id;
		this.boqMasterLmpsId = boqMasterLmpsId;
		this.materialId = materialId;
		this.materialName = materialName;
		this.unitId = unitId;
		this.unitName = unitName;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBoqMasterLmpsId() {
		return boqMasterLmpsId;
	}

	public void setBoqMasterLmpsId(Long boqMasterLmpsId) {
		this.boqMasterLmpsId = boqMasterLmpsId;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

}
