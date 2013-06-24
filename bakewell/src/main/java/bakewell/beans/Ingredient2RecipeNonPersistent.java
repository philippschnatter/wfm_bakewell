package bakewell.beans;

public class Ingredient2RecipeNonPersistent {
	private String ingredient_name = null;
	private Integer recipe_id = null;
	private Integer ingredient_id = null;
	private Double amount = null;  // in gram
	
	public Ingredient2RecipeNonPersistent() {
	}
	
	public Ingredient2RecipeNonPersistent(String ingredient_name,
			Integer recipe_id, Integer ingredient_id, Double amount) {
		super();
		this.ingredient_name = ingredient_name;
		this.recipe_id = recipe_id;
		this.ingredient_id = ingredient_id;
		this.amount = amount;
	}
	
	public String getIngredient_name() {
		return ingredient_name;
	}
	public void setIngredient_name(String ingredient_name) {
		this.ingredient_name = ingredient_name;
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
