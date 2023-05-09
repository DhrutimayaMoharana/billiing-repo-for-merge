package erp.workorder.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="unit_type")
public class UnitType implements Serializable {
	
	private static final long serialVersionUID = -7951651912790880179L;

	private Long id;

	private String name;
	
	public UnitType() {
		super();
	}
	
	public UnitType(Long id) {
		super();
		this.id = id;
	}

	public UnitType(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	

}
