package erp.workorder.dto;

import java.util.List;

public class WorkorderBoqWorkRenderDTO {

	private Long id;

	private Long workorderId;

	private List<MinimalWorkorderBoqLocationDTO> locations;

	private List<WorkorderBoqWorkParentChildDTO> boqs;
	
	private String workScope;

	private String annexureNote;

	private Double totalAmount;
	
	private Object structureBoqs;

	public WorkorderBoqWorkRenderDTO() {
		super();
	}

	public WorkorderBoqWorkRenderDTO(Long id, Long workorderId, List<MinimalWorkorderBoqLocationDTO> locations,
			List<WorkorderBoqWorkParentChildDTO> boqs, String workScope, String annexureNote) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.locations = locations;
		this.boqs = boqs;
		this.workScope = workScope;
		this.annexureNote = annexureNote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public List<WorkorderBoqWorkParentChildDTO> getBoqs() {
		return boqs;
	}

	public void setBoqs(List<WorkorderBoqWorkParentChildDTO> boqs) {
		this.boqs = boqs;
	}

	public String getWorkScope() {
		return workScope;
	}

	public void setWorkScope(String workScope) {
		this.workScope = workScope;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<MinimalWorkorderBoqLocationDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<MinimalWorkorderBoqLocationDTO> locations) {
		this.locations = locations;
	}

	public String getAnnexureNote() {
		return annexureNote;
	}

	public void setAnnexureNote(String annexureNote) {
		this.annexureNote = annexureNote;
	}

	public Object getStructureBoqs() {
		return structureBoqs;
	}

	public void setStructureBoqs(Object structureBoqs) {
		this.structureBoqs = structureBoqs;
	}

}
