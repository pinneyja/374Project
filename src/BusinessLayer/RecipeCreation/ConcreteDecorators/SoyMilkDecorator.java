package BusinessLayer.RecipeCreation.ConcreteDecorators;

import BusinessLayer.RecipeCreation.IngredientDecorator;
import BusinessLayer.RecipeCreation.Recipe;
import BusinessLayer.RecipeCreation.RecipeStep;

import java.util.ArrayList;

public class SoyMilkDecorator implements IngredientDecorator {

	Recipe recipe;
	RecipeStep recStep = new RecipeStep("steam", "soymilk"); //add recstep
	
	public SoyMilkDecorator(Recipe recipe)
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
