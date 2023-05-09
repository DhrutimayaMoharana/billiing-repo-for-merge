package erp.workorder.dto;

public class SiteWorkorderDTO {

	private Long siteId;

	private Integer workorderCount;

	public SiteWorkorderDTO() {
		super();
	}

	public SiteWorkorderDTO(Long siteId, Integer workorderCount) {
		super();
		this.siteId = siteId;
		this.workorderCount = workorderCount;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Integer getWorkorderCount() {
		return workorderCount;
	}

	public void setWorkorderCount(Integer workorderCount) {
		this.workorderCount = workorderCount;
	}

}
