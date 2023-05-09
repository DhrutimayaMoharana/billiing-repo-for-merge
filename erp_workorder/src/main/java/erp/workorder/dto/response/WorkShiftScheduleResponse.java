package erp.workorder.dto.response;

public class WorkShiftScheduleResponse {

	private Integer id;

	private Integer shiftHours;

	public WorkShiftScheduleResponse() {
		super();
	}

	public WorkShiftScheduleResponse(Integer id, Integer shiftHours) {
		super();
		this.id = id;
		this.shiftHours = shiftHours;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShiftHours() {
		return shiftHours;
	}

	public void setShiftHours(Integer shiftHours) {
		this.shiftHours = shiftHours;
	}

}
