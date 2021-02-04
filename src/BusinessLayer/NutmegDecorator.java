package BusinessLayer;

import java.util.ArrayList;

public class NutmegDecorator implements IngredientDecorator{

	Recipe recipe;
	RecipeStep recStep = new RecipeStep("add", "nutmeg"); //add recstep
	
	public NutmegDecorator(Recipe recipe)
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
