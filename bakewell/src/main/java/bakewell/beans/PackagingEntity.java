package bakewell.beans;
 
import java.io.Serializable;
import java.util.Date;
 
public class PackagingEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7709029375603112724L;
	
	private int id;
	
	private String packagerName;
	private double packagerCost;
	private String packagerDestination;
	private Date packagerDelivery;
	private Date packagerTermination;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPackagerName() {
		return packagerName;
	}
	public void setPackagerName(String packagerName) {
		this.packagerName = packagerName;
	}
	public double getPackagerCost() {
		return packagerCost;
	}
	public void setPackagerCost(double packagerCost) {
		this.packagerCost = packagerCost;
	}
	public String getPackagerDestination() {
		return packagerDestination;
	}
	public void setPackagerDestination(String packagerDestination) {
		this.packagerDestination = packagerDestination;
	}
	public Date getPackagerDelivery() {
		return packagerDelivery;
	}
	public void setPackagerDelivery(Date packagerDelivery) {
		this.packagerDelivery = packagerDelivery;
	}
	public Date getPackagerTermination() {
		return packagerTermination;
	}
	public void setPackagerTermination(Date packagerTermination) {
		this.packagerTermination = packagerTermination;
	}
	
}

