package BusinessLayer.RecipeCreation.ConcreteRecipes;

import BusinessLayer.RecipeCreation.Recipe;
import BusinessLayer.RecipeCreation.RecipeStep;

import java.util.ArrayList;

public class PumpkinSpiceRecipe implements Recipe {

	
	ArrayList<RecipeStep> recipeSteps = new ArrayList<RecipeStep>();

	
	public PumpkinSpiceRecipe()
	{
		recipeSteps.add(new RecipeStep("add","coffee"));
		recipeSteps.add(new RecipeStep("add","pumpkin spice"));
		recipeSteps.add(new RecipeStep("add","cream"));
		recipeSteps.add(new RecipeStep("mix",""));
		recipeSteps.add(new RecipeStep("top","nutmeg"));
	}
	
	
	@Override
	public ArrayList<RecipeStep> buildRecipe() {
		// TODO Auto-generated method stub
		return this.recipeSteps;
	}

}
