package bakewell.jsf;

/**
 * @author Alex K
 *
 */
public class orderTransport {
	private String transport_Contractor = "";
	private Double transport_cost = null;
	
	
	public String getTransport_Contractor() {
		return transport_Contractor;
	}


	public void setTransport_Contractor(String transport_Contractor) {
		this.transport_Contractor = transport_Contractor;
	}


	public Double getTransport_cost() {
		return transport_cost;
	}


	public void setTransport_cost(String transport_cost) {
		this.transport_cost = Double.parseDouble(transport_cost);
	}


	public String proceed(){
		return "proceed";
	}
}
