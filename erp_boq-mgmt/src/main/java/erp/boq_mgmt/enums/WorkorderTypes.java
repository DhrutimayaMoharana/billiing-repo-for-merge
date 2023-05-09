package erp.boq_mgmt.enums;

public enum WorkorderTypes {

	Highway(1), Structure(2), Consultant(3), Borewell(7), CRMB(8), UTILITYSHIFTING(9), TAPLOOZFALSECEILING(10),
	ROWPILLAR(11), REWALL(12);

	private int typeId;

	WorkorderTypes(int typeId) {
		this.typeId = typeId;
	}

	public int getTypeId() {
		return typeId;
	}

}
