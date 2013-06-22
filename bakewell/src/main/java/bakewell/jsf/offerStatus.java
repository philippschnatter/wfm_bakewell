package bakewell.jsf;

/**
 * @author Alex K
 *
 */
public class offerStatus {
	private String status;
	
	

	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String proceed(){
		return "proceed";
	}
}
