package BusinessLayer.Commander;

import java.util.ArrayList;

import BusinessLayer.RecipeCreation.Recipe;
import BusinessLayer.RecipeCreation.RecipeStep;

//A command class used for Building the recipe
public class BuildRecipeCommand implements Commander {

	//Takes in a recipe in the constructor
	Recipe recipe;
	
	//We store the result of the execute in here since it implements the execution as a void from the BusinessLayer.Commander interface
	public ArrayList<RecipeStep>  recipeSteps = new ArrayList<RecipeStep>();
	
	public BuildRecipeCommand(Recipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	//calls the buildRecipe on the recipe passed in the constructor and sets our recipeSteps to the call's return
	public  void execute() {
		// TODO Auto-generated method stub
		this.recipeSteps = recipe.buildRecipe();

	}
	
	//gets the recipe steps
	public ArrayList<RecipeStep> getExecute()
	{
		return this.recipeSteps;
	}

	
	
	
	
}
