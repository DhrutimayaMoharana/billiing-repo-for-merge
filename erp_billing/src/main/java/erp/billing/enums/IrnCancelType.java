package erp.billing.enums;

public enum IrnCancelType {

	Duplicate(1, "Duplicate"), Data_Entry_Mistake(2, "Data entry mistake"), Order_Cancelled(3, "Order Cancelled"),
	Others(4, "Others");

	private Integer id;

	private String name;

	private IrnCancelType(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
