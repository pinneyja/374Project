package BusinessLayer.RecipeCreation.ConcreteRecipes;

import java.util.ArrayList;

import BusinessLayer.RecipeCreation.Recipe;
import BusinessLayer.RecipeCreation.RecipeStep;

public class ConcreteRecipe implements Recipe{

	String drinkType;
	ArrayList<RecipeStep> recipeSteps = new ArrayList<RecipeStep>();

	public ConcreteRecipe (String drinkType, ArrayList<RecipeStep> recipeSteps) 
	{
		this.drinkType = drinkType;
		this.recipeSteps = recipeSteps;
	}
	
	
	@Override
	public ArrayList<RecipeStep> buildRecipe() {
		// TODO Auto-generated method stub
		return this.recipeSteps;
	}
	

}
