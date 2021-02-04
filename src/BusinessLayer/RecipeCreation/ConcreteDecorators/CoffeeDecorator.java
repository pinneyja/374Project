package BusinessLayer.RecipeCreation.ConcreteDecorators;

import BusinessLayer.RecipeCreation.IngredientDecorator;
import BusinessLayer.RecipeCreation.Recipe;
import BusinessLayer.RecipeCreation.RecipeStep;

import java.util.ArrayList;

public class CoffeeDecorator implements IngredientDecorator {
	
	Recipe recipe;
	RecipeStep recipeStep = new RecipeStep("add", "coffee"); //add recstep
	
	public CoffeeDecorator(Recipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public ArrayList<RecipeStep> buildRecipe() {
		// TODO Auto-generated method stub
		ArrayList<RecipeStep> recipeSteps = this.recipe.buildRecipe();
		recipeSteps.add(recipeStep);
		return recipeSteps;
	}

}
