package erp.workorder.dto.request;

import java.util.List;

import erp.workorder.dto.DieselRateMappingDTO;

public class DieselRateFetchRequest {

	List<DieselRateMappingDTO> dieselRatesList;

	Double weightedRate;

	public DieselRateFetchRequest() {
		super();
	}

	public DieselRateFetchRequest(List<DieselRateMappingDTO> dieselRatesList, Double weightedRate) {
		super();
		this.dieselRatesList = dieselRatesList;
		this.weightedRate = weightedRate;
	}

	public List<DieselRateMappingDTO> getDieselRatesList() {
		return dieselRatesList;
	}

	public void setDieselRatesList(List<DieselRateMappingDTO> dieselRatesList) {
		this.dieselRatesList = dieselRatesList;
	}

	public Double getWeightedRate() {
		return weightedRate;
	}

	public void setWeightedRate(Double weightedRate) {
		this.weightedRate = weightedRate;
	}

}
