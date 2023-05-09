package erp.workorder.dto;

public class NextStateTransitDTO {

	private Integer nextStateId;

	private String nextStateName;

	private String nextStateAlias;

	private Integer nextRoleId;

	private String nextRoleName;

	private Boolean isButtonRequired;

	private String rgbColorCode;

	private String buttonText;

	public NextStateTransitDTO() {
		super();
	}

	public NextStateTransitDTO(Integer nextStateId, String nextStateName, String nextStateAlias, Integer nextRoleId,
			String nextRoleName, Boolean isButtonRequired, String buttonText) {
		super();
		this.nextStateId = nextStateId;
		this.nextStateName = nextStateName;
		this.nextStateAlias = nextStateAlias;
		this.nextRoleId = nextRoleId;
		this.nextRoleName = nextRoleName;
		this.isButtonRequired = isButtonRequired;
		this.buttonText = buttonText;
	}

	public Integer getNextStateId() {
		return nextStateId;
	}

	public void setNextStateId(Integer nextStateId) {
		this.nextStateId = nextStateId;
	}

	public String getNextStateName() {
		return nextStateName;
	}

	public void setNextStateName(String nextStateName) {
		this.nextStateName = nextStateName;
	}

	public String getNextStateAlias() {
		return nextStateAlias;
	}

	public void setNextStateAlias(String nextStateAlias) {
		this.nextStateAlias = nextStateAlias;
	}

	public Integer getNextRoleId() {
		return nextRoleId;
	}

	public void setNextRoleId(Integer nextRoleId) {
		this.nextRoleId = nextRoleId;
	}

	public String getNextRoleName() {
		return nextRoleName;
	}

	public void setNextRoleName(String nextRoleName) {
		this.nextRoleName = nextRoleName;
	}

	public Boolean getIsButtonRequired() {
		return isButtonRequired;
	}

	public void setIsButtonRequired(Boolean isButtonRequired) {
		this.isButtonRequired = isButtonRequired;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public String getRgbColorCode() {
		return rgbColorCode;
	}

	public void setRgbColorCode(String rgbColorCode) {
		this.rgbColorCode = rgbColorCode;
	}

}
