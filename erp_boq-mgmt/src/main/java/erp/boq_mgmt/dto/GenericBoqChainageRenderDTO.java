package erp.boq_mgmt.dto;

import java.util.List;

public class GenericBoqChainageRenderDTO {

	private GenericBoqMappingDTO genericBoq;

	private List<ChainageGenericBoqQuantityMappingDTO> genericBoqChainages;

	private Double distributedQuantity;

	public GenericBoqChainageRenderDTO(GenericBoqMappingDTO genericBoq,
			List<ChainageGenericBoqQuantityMappingDTO> genericBoqChainages, Double distributedQuantity) {
		super();
		this.genericBoq = genericBoq;
		this.genericBoqChainages = genericBoqChainages;
		this.distributedQuantity = distributedQuantity;
	}

	public GenericBoqChainageRenderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GenericBoqMappingDTO getGenericBoq() {
		return genericBoq;
	}

	public void setGenericBoq(GenericBoqMappingDTO genericBoq) {
		this.genericBoq = genericBoq;
	}

	public List<ChainageGenericBoqQuantityMappingDTO> getGenericBoqChainages() {
		return genericBoqChainages;
	}

	public void setGenericBoqChainages(List<ChainageGenericBoqQuantityMappingDTO> genericBoqChainages) {
		this.genericBoqChainages = genericBoqChainages;
	}

	public Double getDistributedQuantity() {
		return distributedQuantity;
	}

	public void setDistributedQuantity(Double distributedQuantity) {
		this.distributedQuantity = distributedQuantity;
	}

}
