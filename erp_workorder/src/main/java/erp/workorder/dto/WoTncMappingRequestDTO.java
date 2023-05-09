package erp.workorder.dto;

import java.util.List;

public class WoTncMappingRequestDTO {
	
	private Long workorderId;
	
	private List<WoTncMappingDTO> tncs;
	
	private Long siteId;
	
	private Long createdBy;

	public WoTncMappingRequestDTO() {
		super();
	}

	public WoTncMappingRequestDTO(Long workorderId, List<WoTncMappingDTO> tncs, Long siteId, Long createdBy) {
		super();
		this.workorderId = workorderId;
		this.tncs = tncs;
		this.siteId = siteId;
		this.createdBy = createdBy;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public List<WoTncMappingDTO> getTncs() {
		return tncs;
	}

	public void setTncs(List<WoTncMappingDTO> tncs) {
		this.tncs = tncs;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

}
