package bakewell.beans;

import javax.xml.bind.annotation.XmlRootElement;
//import javax.inject.Named;


@XmlRootElement(name="Ingredient")
//@Named
public class Ingredient {
	
	private Integer id = null;
	private String name = null;
	private Double fat = null;
	private Double sugar = null;
	private Double calories = null;
	private Double protein = null;
	
	public Ingredient() {

	}

	public Ingredient(String name, Double fat, Double sugar, Double calories, Double protein)
	{
		this.name = name;
		this.fat = fat;
		this.sugar = sugar;
		this.calories = calories;
		this.protein = protein;
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
	public Double getFat() {
		return fat;
	}
	public void setFat(Double fat) {
		this.fat = fat;
	}
	public Double getSugar() {
		return sugar;
	}
	public void setSugar(Double sugar) {
		this.sugar = sugar;
	}
	public Double getCalories() {
		return calories;
	}
	public void setCalories(Double calories) {
		this.calories = calories;
	}
	public Double getProtein() {
		return protein;
	}
	public void setProtein(Double protein) {
		this.protein = protein;
	}
	
	public String toString()
	{
		return this.name+", "+this.fat+", "+this.sugar+", "+this.calories+", "+this.protein;
	}
	
	
}
