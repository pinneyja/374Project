package BusinessLayer.RecipeCreation.ConcreteDecorators;

import java.util.ArrayList;

import BusinessLayer.RecipeCreation.IngredientDecorator;
import BusinessLayer.RecipeCreation.Recipe;
import BusinessLayer.RecipeCreation.RecipeStep;

public class ConcreteDecorator implements IngredientDecorator {

	Recipe recipe;
	RecipeStep recipeStep; // = new RecipeStep("add", "decaffcoffee"); //add recstep
	//recstep will be determined via what is in the database and using the string passed in
	
	public ConcreteDecorator(Recipe recipe, RecipeStep recipeStep)
	{
		this.recipe = recipe;
		this.recipeStep = recipeStep;
	}
	
	
	@Override
	public ArrayList<RecipeStep> buildRecipe() {
		ArrayList<RecipeStep> recipeSteps = this.recipe.buildRecipe();
		recipeSteps.add(recipeStep);
		return recipeSteps;
	}

}
