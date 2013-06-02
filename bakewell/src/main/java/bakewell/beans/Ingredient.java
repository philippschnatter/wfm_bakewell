package bakewell.beans;

public class Ingredient {
	
	private Integer id = null;
	private String name = null;
	private Double amount = null;
	private Double fat = null;
	private Double sugar = null;
	private Double calories = null;
	
	public Ingredient() {

	}

	public Ingredient(String name, Double amount, Double fat, Double sugar, Double calories)
	{
		this.name = name;
		this.amount = amount;
		this.fat = fat;
		this.sugar = sugar;
		this.calories = calories;
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
}
