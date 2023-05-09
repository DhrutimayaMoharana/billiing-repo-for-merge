package erp.workorder.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "material_stock")
public class MaterialStock {
	
	private Long id;
	
	private Long materialId;
	
	private Double weightedRate;
	
	private Date createdOn;
	
	private Long siteId;

	public MaterialStock() {
		super();
	}

	public MaterialStock(Long id) {
		super();
		this.id = id;
	}

	public MaterialStock(Long id, Long materialId, Double weightedRate, Date createdOn, Long siteId) {
		super();
		this.id = id;
		this.materialId = materialId;
		this.weightedRate = weightedRate;
		this.createdOn = createdOn;
		this.siteId = siteId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="material_id")
	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	@Column(name="weighted_rate")
	public Double getWeightedRate() {
		return weightedRate;
	}

	public void setWeightedRate(Double weightedRate) {
		this.weightedRate = weightedRate;
	}

	@Column(name="created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

}
