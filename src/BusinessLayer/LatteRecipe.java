package BusinessLayer;

import java.util.ArrayList;

public class LatteRecipe implements Recipe{

	ArrayList<RecipeStep> recipeSteps = new ArrayList<RecipeStep>();
	

	
	public LatteRecipe()
	{
		recipeSteps.add(new RecipeStep("steam","milk"));
		recipeSteps.add(new RecipeStep("add","expresso"));
		recipeSteps.add(new RecipeStep("top","whipped cream"));
	}
	
	
	@Override
	public ArrayList<RecipeStep> buildRecipe() {
		// TODO Auto-generated method stub
		return this.recipeSteps;
	}

}
