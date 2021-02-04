package BusinessLayer.RecipeCreation.ConcreteDecorators;

import BusinessLayer.RecipeCreation.IngredientDecorator;
import BusinessLayer.RecipeCreation.Recipe;
import BusinessLayer.RecipeCreation.RecipeStep;

import java.util.ArrayList;

public class DecaffCoffeeDecorator implements IngredientDecorator {
	Recipe recipe;
	RecipeStep recStep = new RecipeStep("add", "decaffcoffee"); //add recstep
	
	public DecaffCoffeeDecorator(Recipe recipe)
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
