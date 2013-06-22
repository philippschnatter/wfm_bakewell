package bakewell.beans;

public class Ingredient2Recipe {

	private Integer id = null;
	private Integer recipe_id = null;
	private Integer ingredient_id = null;
	private Double amount = null;  // in gram
	
	public Ingredient2Recipe() {
		
	}
	
	public Ingredient2Recipe(Integer recipe_id,
			Integer ingredient_id, Double amount) {
		this.recipe_id = recipe_id;
		this.ingredient_id = ingredient_id;
		this.amount = amount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(Integer recipe_id) {
		this.recipe_id = recipe_id;
	}
	public Integer getIngredient_id() {
		return ingredient_id;
	}
	public void setIngredient_id(Integer ingredient_id) {
		this.ingredient_id = ingredient_id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
