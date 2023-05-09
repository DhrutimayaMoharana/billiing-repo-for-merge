package erp.boq_mgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "erp_files")
public class FileEntity implements Serializable {

	private static final long serialVersionUID = 5046459703529152781L;

	private Long id;

	private String name;

	private String path;

	private Date createdOn;

	private Long createdBy;

	private Integer moduleId;

	private Module module;

	public FileEntity() {
		super();
	}

	public FileEntity(Long id) {
		super();
		this.id = id;
	}

	public FileEntity(Long id, String name, String path, Date createdOn, Long createdBy, Integer moduleId) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.moduleId = moduleId;
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

	@Column(name = "path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "module_id")
	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	@OneToOne
	@JoinColumn(name = "module_id", insertable = false, updatable = false)
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}
