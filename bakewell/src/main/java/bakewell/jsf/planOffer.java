package bakewell.jsf;

/**
 * @author Alex K
 *
 */
public class planOffer {
	private Double transport_cost = null;
	private Double packagingCost=null;
	private Double bidPrice=null;
	
	public Double getTransport_cost() {
		return transport_cost;
	}



	public void setTransport_cost(Double transport_cost) {
		this.transport_cost = transport_cost;
	}



	public Double getPackagingCost() {
		return packagingCost;
	}



	public void setPackagingCost(Double packagingCost) {
		this.packagingCost = packagingCost;
	}


	public Double getBidPrice() {
		return bidPrice;
	}

	
	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public String proceed(){
		return "proceed";
	}
}
