package erp.billing.dto.response;

public class PaginationResultObject {

	private int count;

	private Object dataList;

	public PaginationResultObject() {
		super();
	}

	public PaginationResultObject(int count, Object dataList) {
		super();
		this.count = count;
		this.dataList = dataList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Object getDataList() {
		return dataList;
	}

	public void setDataList(Object dataList) {
		this.dataList = dataList;
	}

}
