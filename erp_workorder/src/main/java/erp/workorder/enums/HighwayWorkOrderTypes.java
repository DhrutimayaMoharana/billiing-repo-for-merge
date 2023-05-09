package erp.workorder.enums;

public enum HighwayWorkOrderTypes {

	Highway(1), Borewell(7), CRMB(8), Utility_Shifting(9), Taplooz_false_ceiling(10), Row_Pillar(11), Re_wall(12),
	Pile_work(13), Pile_Chipping(14), Pile_Load_Test(15), Jay_Kay_Ply(16), DESIGN(17);

	private int typeId;

	HighwayWorkOrderTypes(int typeId) {
		this.typeId = typeId;
	}

	public int getTypeId() {
		return typeId;
	}

}
