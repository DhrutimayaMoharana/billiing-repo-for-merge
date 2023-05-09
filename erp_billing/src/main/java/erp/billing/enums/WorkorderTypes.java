package erp.billing.enums;
public enum WorkorderTypes {

	Highway(1), Structure(2), Consultancy(3), Machine_Hiring(4);

	private int value;

	WorkorderTypes(Integer value) {
		this.value = value;
	}

	public int getId() {
		return value;
	}
}
