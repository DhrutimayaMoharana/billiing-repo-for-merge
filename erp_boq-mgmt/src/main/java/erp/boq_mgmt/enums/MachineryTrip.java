package erp.boq_mgmt.enums;

public enum MachineryTrip {

	Loaded(0), Unloaded(1);

	private int id;

	MachineryTrip(Integer id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
