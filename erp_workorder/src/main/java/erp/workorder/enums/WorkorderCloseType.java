package erp.workorder.enums;

public enum WorkorderCloseType {

	CANCEL(0, "Cancel"), FULL_AND_FINAL(1, "Full And Final");

	private int id;

	private String name;

	private WorkorderCloseType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
