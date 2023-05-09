package erp.workorder.enums;

public enum MachineryAttendanceStatus {

	PRESENT(0), HALFDAY(1);

	private int id;

	MachineryAttendanceStatus(Integer id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
