package BusinessLayer;

import java.util.ArrayList;

public class WhippedCreamDecorator implements IngredientDecorator {
	Recipe recipe;
	RecipeStep recStep = new RecipeStep("top", "decaffcoffee"); //add recstep
	
	public WhippedCreamDecorator(Recipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public ArrayList<RecipeStep> buildRecipe() {
		// TODO Auto-generated method stub
		ArrayList<RecipeStep> recipeSteps = this.recipe.buildRecipe();
		recipeSteps.add(recStep);
		return recipeSteps;
	}
}