package bakewell.jsf;

/**
 * @author Alex K
 *
 */
public class customerInformation {
	private String mailAddress = null;
	private String company = null;
	private String problemMessage = null;
	private String additionalMessage = null;
	private String product_Name = null;
	
	
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


	public String getProblemMessage() {
		return problemMessage;
	}


	public void setProblemMessage(String problemMessage) {
		this.problemMessage = problemMessage;
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


	public String send(){
		return "send";
	}
}
