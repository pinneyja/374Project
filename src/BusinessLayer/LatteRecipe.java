package BusinessLayer;

import java.util.ArrayList;

public class LatteRecipe implements Recipe{

	ArrayList<RecipeStep> recipeSteps; // = {array}
	
	public LatteRecipe()
	{
		
	}
	
	
	@Override
	public ArrayList<RecipeStep> buildRecipe() {
		// TODO Auto-generated method stub
		return this.recipeSteps;
	}

}
