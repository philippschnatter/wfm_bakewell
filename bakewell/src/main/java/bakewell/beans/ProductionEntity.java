package bakewell.beans;
 
import java.io.Serializable;
import java.util.Date;
 
public class ProductionEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7709029375603112724L;
	
	private int id;
	
	private String facilityName;
	private int ovens;
	private Date productionStart;
	private Date productionEnd;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public int getOvens() {
		return ovens;
	}
	public void setOvens(int ovens) {
		this.ovens = ovens;
	}
	public Date getProductionStart() {
		return productionStart;
	}
	public void setProductionStart(Date productionStart) {
		this.productionStart = productionStart;
	}
	public Date getProductionEnd() {
		return productionEnd;
	}
	public void setProductionEnd(Date productionEnd) {
		this.productionEnd = productionEnd;
	}
}

