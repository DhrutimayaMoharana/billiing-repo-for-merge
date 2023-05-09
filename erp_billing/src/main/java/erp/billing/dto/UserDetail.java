package erp.billing.dto;

public class UserDetail {

	private Long id;

	private String code;

	private String name;

	private Integer roleId;

	private Integer companyId;

	public UserDetail() {
		super();
	}

	public UserDetail(Long id) {
		super();
		this.id = id;
	}

	public UserDetail(Long id, String code, String name, Integer roleId, Integer companyId) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.roleId = roleId;
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
