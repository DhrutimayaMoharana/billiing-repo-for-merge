package erp.boq_mgmt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "structure")
public class StructureV2 implements Serializable {

	private static final long serialVersionUID = -42180280061668087L;

	private Long id;

	private String name;

	private StructureType type;

	private Long siteId;

	private Boolean isActive;

	public StructureV2() {
		super();
	}

	public StructureV2(Long id) {
		super();
		this.id = id;
	}

	public StructureV2(Long id, String name, StructureType type, Long siteId, Boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.siteId = siteId;
		this.isActive = isActive;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	public StructureType getType() {
		return type;
	}

	public void setType(StructureType type) {
		this.type = type;
	}

}
