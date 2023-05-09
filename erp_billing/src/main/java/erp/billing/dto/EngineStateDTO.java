package erp.billing.dto;

import java.util.Date;

public class EngineStateDTO {

	private Integer id;

	private String name;

	private String alias;

	private String rgbColorCode;

	private String buttonText;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	public EngineStateDTO() {
		super();
	}

	public EngineStateDTO(Integer id) {
		super();
		this.id = id;
	}

	public EngineStateDTO(Integer id, String name, String alias, String rgbColor, String buttonText, Boolean isActive,
			Date createdOn, Integer createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.setAlias(alias);
		this.buttonText = buttonText;
		this.rgbColorCode = rgbColor;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getRgbColorCode() {
		return rgbColorCode;
	}

	public void setRgbColorCode(String rgbColor) {
		this.rgbColorCode = rgbColor;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

}
