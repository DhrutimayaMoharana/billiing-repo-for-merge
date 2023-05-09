package erp.workorder.dto.response;

import erp.workorder.enums.MachineryShifts;

public class WorkorderHiringMachineRateDetailsResponse {

	private Integer shiftId;

	private MachineryShifts shift;

	private Double rate;

	public WorkorderHiringMachineRateDetailsResponse() {
		super();
	}

	public WorkorderHiringMachineRateDetailsResponse(Integer shiftId, MachineryShifts shift, Double rate) {
		super();
		this.shiftId = shiftId;
		this.shift = shift;
		this.rate = rate;
	}

	public Integer getShiftId() {
		return shiftId;
	}

	public void setShiftId(Integer shiftId) {
		this.shiftId = shiftId;
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
