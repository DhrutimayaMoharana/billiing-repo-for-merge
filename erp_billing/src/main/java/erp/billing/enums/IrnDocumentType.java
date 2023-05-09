package erp.billing.enums;

public enum IrnDocumentType {

	INV(0, "INV", "INVOICE"), CRN(1, "CRN", "CREDIT NOTE"), DBN(2, "DBN", "DEBIT NOTE");

	private int id;

	private String name;

	private String description;

	private IrnDocumentType(int id, String name, String description) {
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
