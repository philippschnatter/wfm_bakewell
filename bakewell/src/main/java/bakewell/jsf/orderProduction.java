package bakewell.jsf;

import java.util.Date;

/**
 * @author Alex K
 *
 */
public class orderProduction {
	private Date production_Start = null;
	private Date production_End = null;
	private String production_Facility = null;
	private String production_Contractor = null;
	
	
	public Date getProduction_Start() {
		return production_Start;
	}


	@SuppressWarnings("deprecation")
	public void setProduction_Start(String production_Start) {
		this.production_Start = new Date(production_Start);
	}


	public Date getProduction_End() {
		return production_End;
	}


	public void setProduction_End(Date production_End) {
		this.production_End = production_End;
	}


	public String getProduction_Facility() {
		return production_Facility;
	}


	public void setProduction_Facility(String production_Facility) {
		this.production_Facility = production_Facility;
	}


	public String getProduction_Contractor() {
		return production_Contractor;
	}


	public void setProduction_Contractor(String production_Contractor) {
		this.production_Contractor = production_Contractor;
	}


	public String proceed(){
		return "proceed";
	}
}
