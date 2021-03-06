package bakewell.beans;

import javax.xml.bind.annotation.XmlRootElement;
//import javax.inject.Named;


@XmlRootElement(name="Ingredient")
//@Named
public class Ingredient {
	
	private Integer id = null;
	private String name = null;
	private Double gda_energy = null;		// in kcal
	private Double gda_protein = null;		// in gram per 100 gram
	private Double gda_carbo = null;		// in gram per 100 gram
	private Double gda_fat = null;			// in gram per 100 gram	
	private Double gda_fiber = null;		// in gram per 100 gram
	private Double gda_sodium = null;		// in gram per 100 gram
	private Double price = null;			// in Euro Cents per 100 gram
	
	public Ingredient() {

	}
	
	public Ingredient(String name, Double gda_energy, Double gda_protein,
			Double gda_carbo, Double gda_fat, Double gda_fiber,
			Double gda_sodium, Double price) {
		this.name = name;
		this.gda_energy = gda_energy;
		this.gda_protein = gda_protein;
		this.gda_carbo = gda_carbo;
		this.gda_fat = gda_fat;
		this.gda_fiber = gda_fiber;
		this.gda_sodium = gda_sodium;
		this.price = price;
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

	public Double getGda_energy() {
		return gda_energy;
	}

	public void setGda_energy(Double gda_energy) {
		this.gda_energy = gda_energy;
	}

	public Double getGda_protein() {
		return gda_protein;
	}

	public void setGda_protein(Double gda_protein) {
		this.gda_protein = gda_protein;
	}

	public Double getGda_carbo() {
		return gda_carbo;
	}

	public void setGda_carbo(Double gda_carbo) {
		this.gda_carbo = gda_carbo;
	}

	public Double getGda_fat() {
		return gda_fat;
	}

	public void setGda_fat(Double gda_fat) {
		this.gda_fat = gda_fat;
	}

	public Double getGda_fiber() {
		return gda_fiber;
	}

	public void setGda_fiber(Double gda_fiber) {
		this.gda_fiber = gda_fiber;
	}

	public Double getGda_sodium() {
		return gda_sodium;
	}

	public void setGda_sodium(Double gda_sodium) {
		this.gda_sodium = gda_sodium;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return name+" "+gda_energy+" "+gda_protein+" "+
		gda_carbo+" "+gda_fat+" "+gda_fiber+" "+gda_sodium+" "+price;
	}
	
}