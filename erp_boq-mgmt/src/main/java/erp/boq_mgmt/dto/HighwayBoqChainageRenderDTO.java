package erp.boq_mgmt.dto;

import java.util.List;


public class HighwayBoqChainageRenderDTO {
	
	private HighwayBoqMappingDTO highwayBoq;
	
	private List<ChainageBoqQuantityMappingDTO> highwayBoqChainages;
	
	private Double distributedQuantity;

	public HighwayBoqChainageRenderDTO(HighwayBoqMappingDTO highwayBoq,
			List<ChainageBoqQuantityMappingDTO> highwayBoqChainages, Double distributedQuantity) {
		super();
		this.highwayBoq = highwayBoq;
		this.highwayBoqChainages = highwayBoqChainages;
		this.setDistributedQuantity(distributedQuantity);
	}

	public HighwayBoqMappingDTO getHighwayBoq() {
		return highwayBoq;
	}

	public void setHighwayBoq(HighwayBoqMappingDTO highwayBoq) {
		this.highwayBoq = highwayBoq;
	}

	public List<ChainageBoqQuantityMappingDTO> getHighwayBoqChainages() {
		return highwayBoqChainages;
	}

	public void setHighwayBoqChainages(List<ChainageBoqQuantityMappingDTO> highwayBoqChainages) {
		this.highwayBoqChainages = highwayBoqChainages;
	}

	public Double getDistributedQuantity() {
		return distributedQuantity;
	}

	public void setDistributedQuantity(Double distributedQuantity) {
		this.distributedQuantity = distributedQuantity;
	}

}
