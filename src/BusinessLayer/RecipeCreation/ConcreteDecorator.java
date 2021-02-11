package BusinessLayer.RecipeCreation;

import java.util.ArrayList;

//Decorates Concrete Recipes
public class ConcreteDecorator implements IngredientDecorator {

	//Has a recipe it wraps and a recipe step
	Recipe recipe;
	RecipeStep recipeStep; 
	
	public ConcreteDecorator(Recipe recipe, RecipeStep recipeStep)
	{
		this.recipe = recipe;
		this.recipeStep = recipeStep;
	}
	
	
	@Override //Build recipe gets the recipe steps within the recipe it wraps, adds the recipe step of the decorator and returns the new array list of steps
	public ArrayList<RecipeStep> buildRecipe() {
		ArrayList<RecipeStep> recipeSteps = this.recipe.buildRecipe();
		recipeSteps.add(recipeStep);
		return recipeSteps;
	}

}
