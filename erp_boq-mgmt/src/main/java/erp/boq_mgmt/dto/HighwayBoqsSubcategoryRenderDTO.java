package erp.boq_mgmt.dto;

import java.util.List;

public class HighwayBoqsSubcategoryRenderDTO {
	
	private Long id;
	
	private String name;
	
	private List<HighwayBoqItemsRenderDTO> boqs;

	public HighwayBoqsSubcategoryRenderDTO() {
		super();
	}

	public HighwayBoqsSubcategoryRenderDTO(Long id, String name, List<HighwayBoqItemsRenderDTO> boqs) {
		super();
		this.id = id;
		this.name = name;
		this.boqs = boqs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HighwayBoqItemsRenderDTO> getBoqs() {
		return boqs;
	}

	public void setBoqs(List<HighwayBoqItemsRenderDTO> boqs) {
		this.boqs = boqs;
	}

}
