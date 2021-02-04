package BusinessLayer;

import java.util.ArrayList;

public class CoffeeDecorator implements IngredientDecorator{
	
	Recipe recipe;
	RecipeStep recStep; //add recstep
	
	public CoffeeDecorator(Recipe recipe)
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
