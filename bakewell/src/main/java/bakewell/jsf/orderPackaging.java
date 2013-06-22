package bakewell.jsf;

/**
 * @author Alex K
 *
 */
public class orderPackaging {
	private String packagingContractor;
	private double packagingCost;
	
	
	public String getPackagingContractor() {
		return packagingContractor;
	}


	public void setPackagingContractor(String packagingContractor) {
		this.packagingContractor = packagingContractor;
	}


	public double getPackagingCost() {
		return packagingCost;
	}


	public void setPackagingCost(String packagingCost) {
		this.packagingCost = Double.parseDouble(packagingCost);
	}


	public String proceed(){
		return "proceed";
	}
}
