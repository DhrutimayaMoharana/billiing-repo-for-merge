package erp.billing.enums;

public enum GstTypeEnum {

	GOODS(0, "Goods"), SERVICES(1, "Services");

	private int id;

	private String description;

	GstTypeEnum(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

}
