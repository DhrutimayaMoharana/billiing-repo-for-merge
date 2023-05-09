package erp.workorder.dto;

import java.util.Date;

public class CurrencyDTO {
	
	private Long id;
	
	private String name;
	
	private String code;
	
	private Boolean isActive;
	
	private Date createdOn;

	public CurrencyDTO() {
		super();
	}

	public CurrencyDTO(Long id) {
		super();
		this.id = id;
	}

	public CurrencyDTO(Long id, String name, String code, Boolean isActive, Date createdOn) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.isActive = isActive;
		this.createdOn = createdOn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

}
