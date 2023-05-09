package erp.billing.enums;

public enum DebitNoteStates {
	FullyApproved(3);

	private int id;

	DebitNoteStates(Integer id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
