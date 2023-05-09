package erp.billing.enums;

public enum IrnSupplyType {

	B2B(0, "B2B", "Business to Business"), SEZWP(1, "SEZWP", "SEZ with payment"),
	SEZWOP(2, "SEZWOP", "SEZ without payment"), EXPWP(3, "EXPWP", "Export with Payment"),
	EXPWOP(4, "EXPWOP", "Export without payment"), DEXP(5, "DEXP", "Deemed Export");

	private int id;

	private String name;

	private String description;

	private IrnSupplyType(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}
