package erp.workorder.dto;

import java.util.List;

public class WorkorderListRenderDTO {
	
	private List<WorkorderListDTO> workorders;
	
	private SiteDTO site;

	public WorkorderListRenderDTO() {
		super();
	}

	public WorkorderListRenderDTO(List<WorkorderListDTO> workorders, SiteDTO site) {
		super();
		this.workorders = workorders;
		this.site = site;
	}

	public List<WorkorderListDTO> getWorkorders() {
		return workorders;
	}

	public void setWorkorders(List<WorkorderListDTO> workorders) {
		this.workorders = workorders;
	}

	public SiteDTO getSite() {
		return site;
	}

	public void setSite(SiteDTO site) {
		this.site = site;
	}

}
