package bakewell.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Recipe")
public class Recipe {

	
	private Integer id = null;
	private String name = null;
	private String description = null;
	private Double allgda_energy = null;	// in percent of the recommended GDA
	private Double allgda_protein = null;	// in percent of the recommended GDA
	private Double allgda_carbo = null;		// in percent of the recommended GDA
	private Double allgda_fat = null;		// in percent of the recommended GDA
	private Double allgda_fiber = null;		// in percent of the recommended GDA
	private Double allgda_sodium = null;	// in percent of the recommended GDA
	
	public Recipe() {
		
	}
	
	public Recipe(String name, String description, Double allgda_energy,
			Double allgda_protein, Double allgda_carbo, Double allgda_fat,
			Double allgda_fiber, Double allgda_sodium) {
		this.name = name;
		this.description = description;
		this.allgda_energy = allgda_energy;
		this.allgda_protein = allgda_protein;
		this.allgda_carbo = allgda_carbo;
		this.allgda_fat = allgda_fat;
		this.allgda_fiber = allgda_fiber;
		this.allgda_sodium = allgda_sodium;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAllgda_Energy() {
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return name+" "+description+" "+allgda_energy+" "+
				allgda_protein+" "+allgda_carbo+" "+allgda_fat+" "+
				allgda_fiber+" "+allgda_sodium;
	}
	
}
