package bakewell.beans;

import javax.xml.bind.annotation.XmlRootElement;
//import javax.inject.Named;


@XmlRootElement(name="Ingredient")
//@Named
public class Ingredient {
	
	private Integer id = null;
	private String name = null;
	private Double allgda_energy = null;
	private Double allgda_protein = null;
	private Double allgda_carbo = null;
	private Double allgda_fat = null;
	private Double allgda_fiber = null;
	private Double allgda_sodium = null;
	
	public Ingredient() {

	}

	public Ingredient(String name, Double allgda_energy, Double allgda_protein,
			Double allgda_carbo, Double allgda_fat, Double allgda_fiber,
			Double allgda_sodium) {
		this.name = name;
		this.allgda_energy = allgda_energy;
		this.allgda_protein = allgda_protein;
		this.allgda_carbo = allgda_carbo;
		this.allgda_fat = allgda_fat;
		this.allgda_fiber = allgda_fiber;
		this.allgda_sodium = allgda_sodium;
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

	public Double getAllgda_energy() {
		return allgda_energy;
	}

	public void setAllgda_energy(Double allgda_energy) {
		this.allgda_energy = allgda_energy;
	}

	public Double getAllgda_protein() {
		return allgda_protein;
	}

	public void setAllgda_protein(Double allgda_protein) {
		this.allgda_protein = allgda_protein;
	}

	public Double getAllgda_carbo() {
		return allgda_carbo;
	}

	public void setAllgda_carbo(Double allgda_carbo) {
		this.allgda_carbo = allgda_carbo;
	}

	public Double getAllgda_fat() {
		return allgda_fat;
	}

	public void setAllgda_fat(Double allgda_fat) {
		this.allgda_fat = allgda_fat;
	}

	public Double getAllgda_fiber() {
		return allgda_fiber;
	}

	public void setAllgda_fiber(Double allgda_fiber) {
		this.allgda_fiber = allgda_fiber;
	}

	public Double getAllgda_sodium() {
		return allgda_sodium;
	}

	public void setAllgda_sodium(Double allgda_sodium) {
		this.allgda_sodium = allgda_sodium;
	}
	
	
	
	

	
}