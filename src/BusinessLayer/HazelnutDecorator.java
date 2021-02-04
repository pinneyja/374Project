package BusinessLayer;

import java.util.ArrayList;

public class HazelnutDecorator implements IngredientDecorator{

	Recipe recipe;
	RecipeStep recStep = new RecipeStep("add", "hazelnut"); //add recstep
	
	public HazelnutDecorator(Recipe recipe)
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
