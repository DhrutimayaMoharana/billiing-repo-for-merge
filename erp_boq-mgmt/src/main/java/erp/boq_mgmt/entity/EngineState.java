package erp.boq_mgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "engine_states")
public class EngineState implements Serializable {

	private static final long serialVersionUID = 6540161482841913258L;

	private Integer id;

	private String name;

	private String alias;

	private String rgbColorCode;

	private String buttonText;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	public EngineState() {
		super();
	}

	public EngineState(Integer id) {
		super();
		this.id = id;
	}

	public EngineState(Integer id, String name, String alias, String rgbColor, String buttonText, Boolean isActive,
			Date createdOn, Integer createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
		this.setButtonText(buttonText);
		this.setRgbColorCode(rgbColor);
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by")
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "alias")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Column(name = "rgb_code")
	public String getRgbColorCode() {
		return rgbColorCode;
	}

	public void setRgbColorCode(String rgbColor) {
		this.rgbColorCode = rgbColor;
	}

	@Column(name = "button_text")
	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

}