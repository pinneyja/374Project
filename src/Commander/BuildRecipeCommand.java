package Commander;

import java.util.ArrayList;

import BusinessLayer.RecipeCreation.Recipe;
import BusinessLayer.RecipeCreation.RecipeStep;

public class BuildRecipeCommand implements Commander {

	Recipe recipe;
	
	public ArrayList<RecipeStep>  recipeSteps = new ArrayList<RecipeStep>();
	
	public BuildRecipeCommand(Recipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public  void execute() {
		// TODO Auto-generated method stub
		this.recipeSteps = recipe.buildRecipe();

	}
	
	public ArrayList<RecipeStep> getExecute()
	{
		return this.recipeSteps;
	}

	
	
	
	
}
