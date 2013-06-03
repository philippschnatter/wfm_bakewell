package bakewell.beans;
 
import java.io.Serializable;
 
public class MasterDocumentEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7709029375603112724L;
	
	private int id;
	
	private OfferEntity offerDetails;
	private PackagingEntity packagingDetails;
	private ProductionEntity productionDetails;
	private TransportationEntity transportationDetails;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public OfferEntity getOfferDetails() {
		return offerDetails;
	}
	public void setOfferDetails(OfferEntity offerDetails) {
		this.offerDetails = offerDetails;
	}
	public PackagingEntity getPackagingDetails() {
		return packagingDetails;
	}
	public void setPackagingDetails(PackagingEntity packagingDetails) {
		this.packagingDetails = packagingDetails;
	}
	public ProductionEntity getProductionDetails() {
		return productionDetails;
	}
	public void setProductionDetails(ProductionEntity productionDetails) {
		this.productionDetails = productionDetails;
	}
	public TransportationEntity getTransportationDetails() {
		return transportationDetails;
	}
	public void setTransportationDetails(TransportationEntity transportationDetails) {
		this.transportationDetails = transportationDetails;
	}
	
	
}

