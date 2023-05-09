package erp.boq_mgmt.enums;

public enum MachineryRangeType {

	Consolidated(1), Upto_After(2);

	private int id;

	MachineryRangeType(Integer id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
