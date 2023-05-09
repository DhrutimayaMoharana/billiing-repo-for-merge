package erp.billing.enums;

public enum DebitNoteItemDepartments {

	Diesel(4);

	private int departmentId;

	DebitNoteItemDepartments(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

}
