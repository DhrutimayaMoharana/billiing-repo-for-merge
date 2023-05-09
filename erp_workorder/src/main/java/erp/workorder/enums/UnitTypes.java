package erp.workorder.enums;

public enum UnitTypes {

	NA(1), WEIGHT(2), COUNT(3), LENGTH(4), VOLUME(5), TEMPERATURE(6), TIME(7), AREA(8), SPEED(9), CURRENCY(10),
	RATE(11);

	private long typeId;

	UnitTypes(Integer typeId) {
		this.typeId = typeId;
	}

	public long getTypeId() {
		return typeId;
	}
}
