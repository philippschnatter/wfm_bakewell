package bakewell.beans;
 
import java.io.Serializable;
import java.util.Date;
 
public class TransportationEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7709029375603112724L;
	
	private int id;
	
	private String transporterName;
	private double transporterCost;
	private Date transporterTermination;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTransporterName() {
		return transporterName;
	}
	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}
	public double getTransporterCost() {
		return transporterCost;
	}
	public void setTransporterCost(double transporterCost) {
		this.transporterCost = transporterCost;
	}
	public Date getTransporterTermination() {
		return transporterTermination;
	}
	public void setTransporterTermination(Date transporterTermination) {
		this.transporterTermination = transporterTermination;
	}
	
	
	
	
}
