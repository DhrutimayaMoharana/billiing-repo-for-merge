package erp.boq_mgmt.enums;

public enum WorkorderTypes {

	Highway(1), Structure(2), Consultant(3);

	private int typeId;

	WorkorderTypes(int typeId) {
		this.typeId = typeId;
	}

	public int getTypeId() {
		return typeId;
	}

}
