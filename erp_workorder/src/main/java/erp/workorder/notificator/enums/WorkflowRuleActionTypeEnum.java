package erp.workorder.notificator.enums;

public enum WorkflowRuleActionTypeEnum {

	EMAIL_ALERT(1), IN_APP_NOTIFICATION(2);

	private Integer id;

	private WorkflowRuleActionTypeEnum(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

}
