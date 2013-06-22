package bakewell.jsf;

/**
 * @author Alex K
 *
 */
public class sendOffer {
	private String mailAddress = null;
	private String company = null;
	private String additionalMessage = null;
	private String product_Name = null;
	private Double bidPrice=null;
	
	public String getMailAddress() {
		return mailAddress;
	}


	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getAdditionalMessage() {
		return additionalMessage;
	}


	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}

	
	public String getProduct_Name() {
		return product_Name;
	}


	public void setProduct_Name(String product_Name) {
		this.product_Name = product_Name;
	}
	
	
	public Double getBidPrice() {
		return bidPrice;
	}


	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}


	public String send(){
		return "send";
	}
}
