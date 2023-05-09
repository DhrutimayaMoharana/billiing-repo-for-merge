package erp.workorder.notificator.dto;

import java.util.Set;

public class NotifierWorkflowRuleResponse {

	private Integer actionTypeId;

	private Long workflowRuleActionId;

	private Set<Integer> roleRecipient;

	private String additionalRecipients;

	private String ccRecipients;

	private String title;

	private String subject;

	private String content;

	private String variableName;

	private String variablePattern;

	private String queryVarName;

	private String queryVarPattern;

	private Set<Integer> departmentListIds;

	private Set<Integer> departmentGroupListIds;

	public NotifierWorkflowRuleResponse() {
		super();
	}

	public NotifierWorkflowRuleResponse(Integer actionTypeId, Long workflowRuleActionId, Set<Integer> roleRecipient,
			String additionalRecipients, String ccRecipients, String title, String subject, String content,
			String variableName, String variablePattern, String queryVarName, String queryVarPattern) {
		super();
		this.actionTypeId = actionTypeId;
		this.workflowRuleActionId = workflowRuleActionId;
		this.roleRecipient = roleRecipient;
		this.additionalRecipients = additionalRecipients;
		this.ccRecipients = ccRecipients;
		this.title = title;
		this.subject = subject;
		this.content = content;
		this.variableName = variableName;
		this.variablePattern = variablePattern;
		this.queryVarName = queryVarName;
		this.queryVarPattern = queryVarPattern;
	}

	public Integer getActionTypeId() {
		return actionTypeId;
	}

	public void setActionTypeId(Integer actionTypeId) {
		this.actionTypeId = actionTypeId;
	}

	public Long getWorkflowRuleActionId() {
		return workflowRuleActionId;
	}

	public void setWorkflowRuleActionId(Long workflowRuleActionId) {
		this.workflowRuleActionId = workflowRuleActionId;
	}

	public Set<Integer> getRoleRecipient() {
		return roleRecipient;
	}

	public void setRoleRecipient(Set<Integer> roleRecipient) {
		this.roleRecipient = roleRecipient;
	}

	public String getAdditionalRecipients() {
		return additionalRecipients;
	}

	public void setAdditionalRecipients(String additionalRecipients) {
		this.additionalRecipients = additionalRecipients;
	}

	public String getCcRecipients() {
		return ccRecipients;
	}

	public void setCcRecipients(String ccRecipients) {
		this.ccRecipients = ccRecipients;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getVariablePattern() {
		return variablePattern;
	}

	public void setVariablePattern(String variablePattern) {
		this.variablePattern = variablePattern;
	}

	public String getQueryVarName() {
		return queryVarName;
	}

	public void setQueryVarName(String queryVarName) {
		this.queryVarName = queryVarName;
	}

	public String getQueryVarPattern() {
		return queryVarPattern;
	}

	public void setQueryVarPattern(String queryVarPattern) {
		this.queryVarPattern = queryVarPattern;
	}

	public Set<Integer> getDepartmentListIds() {
		return departmentListIds;
	}

	public void setDepartmentListIds(Set<Integer> departmentListIds) {
		this.departmentListIds = departmentListIds;
	}

	public Set<Integer> getDepartmentGroupListIds() {
		return departmentGroupListIds;
	}

	public void setDepartmentGroupListIds(Set<Integer> departmentGroupListIds) {
		this.departmentGroupListIds = departmentGroupListIds;
	}

}
