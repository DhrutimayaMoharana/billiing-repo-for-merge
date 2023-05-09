package erp.boq_mgmt.dto;

import java.util.List;

public class BorewellBoqChainageRenderDTO {

	private BorewellBoqMappingDTO borewellBoq;

	private List<ChainageBorewellBoqQuantityMappingDTO> borewellBoqChainages;

	private Double distributedQuantity;

	public BorewellBoqChainageRenderDTO(BorewellBoqMappingDTO borewellBoq,
			List<ChainageBorewellBoqQuantityMappingDTO> borewellBoqChainages, Double distributedQuantity) {
		super();
		this.borewellBoq = borewellBoq;
		this.borewellBoqChainages = borewellBoqChainages;
		this.distributedQuantity = distributedQuantity;
	}

	public BorewellBoqChainageRenderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BorewellBoqMappingDTO getBorewellBoq() {
		return borewellBoq;
	}

	public void setBorewellBoq(BorewellBoqMappingDTO borewellBoq) {
		this.borewellBoq = borewellBoq;
	}

	public List<ChainageBorewellBoqQuantityMappingDTO> getBorewellBoqChainages() {
		return borewellBoqChainages;
	}

	public void setBorewellBoqChainages(List<ChainageBorewellBoqQuantityMappingDTO> borewellBoqChainages) {
		this.borewellBoqChainages = borewellBoqChainages;
	}

	public Double getDistributedQuantity() {
		return distributedQuantity;
	}

	public void setDistributedQuantity(Double distributedQuantity) {
		this.distributedQuantity = distributedQuantity;
	}

}
