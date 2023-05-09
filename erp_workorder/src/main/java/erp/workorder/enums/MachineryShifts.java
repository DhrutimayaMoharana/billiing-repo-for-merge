package erp.workorder.enums;

public enum MachineryShifts {

	DAYTIME(0, "Day Time"), NIGHTTIME(1, "Night Time");

	private int id;

	private String description;

	private MachineryShifts(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

}
