package BusinessLayer.RecipeCreation;

import java.util.ArrayList;

//Concrete Recipe class that is constructed via a drink's name and recipe steps gathered from the database
public class ConcreteRecipe implements Recipe{

	
	String drinkType;
	ArrayList<RecipeStep> recipeSteps = new ArrayList<RecipeStep>();

	public ConcreteRecipe (String drinkType, ArrayList<RecipeStep> recipeSteps) 
	{
		this.drinkType = drinkType;
		this.recipeSteps = recipeSteps;
	}
	
	
	@Override
	//For the concrete Recipe it returns the recipe's recipe steps 
	public ArrayList<RecipeStep> buildRecipe() {
		// TODO Auto-generated method stub
		return this.recipeSteps;
	}
	

}
