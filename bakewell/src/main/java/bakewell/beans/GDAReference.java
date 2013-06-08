package bakewell.beans;

public class GDAReference {

	private Integer id = null;
	private String name = null;
	private Double amount = null;
	
	public GDAReference() {
		
	}
	
	public GDAReference(String name, Double amount) {
		super();
		this.name = name;
		this.amount = amount;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	
}
