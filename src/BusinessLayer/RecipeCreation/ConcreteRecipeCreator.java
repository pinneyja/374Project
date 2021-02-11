package BusinessLayer.RecipeCreation;

import BusinessLayer.InterLayerCommunication.Option;
import DataLayer.DatabaseConnection;

import java.util.ArrayList;

//Concrete Factory for building recipes with ingredients
public class ConcreteRecipeCreator implements RecipeCreator {
	
	//Connects to the database to get recipe information
	public DatabaseConnection databaseConnection;
	
	//Establish a connection upon creation of the creator
	public ConcreteRecipeCreator()
	{
		this.databaseConnection = new DatabaseConnection();
	}

	@Override //builds and wraps the recipe object
	public Recipe createRecipe(String drinkType, ArrayList<Option> ingredients) {
		// TODO Auto-generated method stub
		
		ArrayList<RecipeStep> recipeSteps = this.databaseConnection.getRecipeSteps(drinkType); //retrieve from the server the recipe's recipe steps
		
		Recipe recipe = new ConcreteRecipe(drinkType,recipeSteps); //Construct a concrete recipe that uses the recipe steps
		
		//return the result of decorating the recipe
		return decorate(recipe, ingredients);
		
		
	}
	
	//decorates the recipe with ingredients and their respective steps
	public Recipe decorate(Recipe recipe, ArrayList<Option> ingredients) 
	{
		//Iterates through all the ingredients
		for (Option option : ingredients)
		{
			String optionName = option.getName();
			int optionQuantity = option.getQuantity();
			
			RecipeStep recipeStep = new RecipeStep(databaseConnection.getCommandStep(optionName),optionName); //get server info on each step to crete a new recipe step
			
			//wraps the recipe multiple times for the quantity of options
			for (int i = 0; i <optionQuantity; i++)
			{
				recipe = new ConcreteDecorator(recipe,recipeStep); 
			}
			
		}
		
		return recipe;
	}

}
