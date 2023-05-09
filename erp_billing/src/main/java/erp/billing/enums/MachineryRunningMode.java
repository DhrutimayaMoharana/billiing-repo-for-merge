package erp.billing.enums;

public enum MachineryRunningMode {

	TRIP(0, "Trip Wise"), SHIFTS(1, "Shift Wise");

	private int id;

	private String description;

	private MachineryRunningMode(int id, String description) {
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
