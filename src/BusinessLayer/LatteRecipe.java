package BusinessLayer;

import java.util.ArrayList;

public class LatteRecipe implements Recipe{

	ArrayList<RecipeStep> steps; // = {array}
	
	public LatteRecipe()
	{
		
	}
	
	
	@Override
	public ArrayList<RecipeStep> buildRecipe() {
		// TODO Auto-generated method stub
		return this.steps;
	}

}
