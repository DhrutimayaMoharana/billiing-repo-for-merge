package erp.workorder.dto.request;

import erp.workorder.enums.MachineryShifts;

public class WorkorderHiringMachineRateDetailsAddUpdateRequest {

	private MachineryShifts shift;

	private Double rate;

	public WorkorderHiringMachineRateDetailsAddUpdateRequest() {
		super();
	}

	public MachineryShifts getShift() {
		return shift;
	}

	public void setShift(MachineryShifts shift) {
		this.shift = shift;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}
