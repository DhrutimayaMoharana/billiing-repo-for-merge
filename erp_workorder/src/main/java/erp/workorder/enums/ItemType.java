package erp.workorder.enums;

public enum ItemType {
	
	Category(0),
	Subcategory(1),
	Boq(2);
	
	private int code;
	
	ItemType(Integer code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}

}
