package BusinessLayer;

import java.util.ArrayList;

public class PumpkinSpiceRecipe implements Recipe {

	
	ArrayList<RecipeStep> recipeSteps; // = {array}
	
	public PumpkinSpiceRecipe()
	{
		
	}
	
	
	@Override
	public ArrayList<RecipeStep> buildRecipe() {
		// TODO Auto-generated method stub
		return this.recipeSteps;
	}

}
