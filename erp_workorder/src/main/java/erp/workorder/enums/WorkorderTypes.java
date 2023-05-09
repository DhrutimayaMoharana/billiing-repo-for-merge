package erp.workorder.enums;

public enum WorkorderTypes {

	Highway(1), Structure(2), Consultancy(3), Machine_Hiring(4), Transportation(5), Labour_Supply(6), Borewell(7);

	private int value;

	WorkorderTypes(Integer value) {
		this.value = value;
	}

	public int getId() {
		return value;
	}
}
