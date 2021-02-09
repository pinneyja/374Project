package BusinessLayer.RecipeCreation;

import BusinessLayer.InterLayerCommunication.Option;
import BusinessLayer.RecipeCreation.ConcreteDecorators.*;
import BusinessLayer.RecipeCreation.ConcreteRecipes.ConcreteRecipe;
import DataLayer.DatabaseConnection;

import java.util.ArrayList;

public class ConcreteRecipeCreator implements RecipeCreator {
	
	public DatabaseConnection databaseConnection;
	
	public ConcreteRecipeCreator()
	{
		this.databaseConnection = new DatabaseConnection();
	}

	@Override
	public Recipe createRecipe(String drinkType, ArrayList<Option> ingredients) {
		// TODO Auto-generated method stub
		
		ArrayList<RecipeStep> recipeSteps = this.databaseConnection.getRecipeSteps(drinkType); //retrieve from server the steps
		
		Recipe recipe = new ConcreteRecipe(drinkType,recipeSteps);//Add server calls to get the recipe steps here
		
		
		return decorate(recipe, ingredients);
		
//		if  (drinkType.equals("Latte"))
//		{
//			Recipe latteRecipe = new LatteRecipe();
//			
//			return decorate(latteRecipe, ingredients);
//		}
//		
//		else if (drinkType.equals("Pumpkin Spice")) 
//		{
//			Recipe pumpkinSpiceRecipe = new PumpkinSpiceRecipe();
//			
//			return decorate(pumpkinSpiceRecipe, ingredients);
//
//
//		}
		
		
//		return null;
		
		
	}
	
	
	public Recipe decorate(Recipe recipe, ArrayList<Option> ingredients) 
	{
		
		for (Option option : ingredients)
		{
			String optionName = option.getName();
			int optionQuantity = option.getQuantity();
			
			RecipeStep recipeStep = new RecipeStep(databaseConnection.getCommandStep(optionName),optionName); //get server info on step
			
			for (int i = 0; i <optionQuantity; i++)
			{
				recipe = new ConcreteDecorator(recipe,recipeStep);
			}
			
		}
		
//		for (Option option : ingredients)
//		{
//			String optionName = option.getName();
//			int optionQuantity = option.getQuantity();
//			
//			for(int i = 0; i < optionQuantity; i++)
//			{
//				switch (optionName) 
//				{
//				case "Coffee":
//					recipe = new CoffeeDecorator(recipe);
//					break;
//				
//				case "Milk":
//					recipe = new MilkDecorator(recipe);
//					break;
//					
//				case "Soy Milk":
//					recipe = new SoyMilkDecorator(recipe);
//					break;
//					
//				case "Sugar":
//					recipe = new SugarDecorator(recipe);
//					break;
//					
//				case "Decaff Coffee":
//					recipe = new DecaffCoffeeDecorator(recipe);
//					break;
//					
//				case "Whipped Cream":
//					recipe = new WhippedCreamDecorator(recipe);
//					break;
//					
//				case "Hazelnut":
//					recipe = new HazelnutDecorator(recipe);
//					break;
//					
//				case "Pumpkin Spice":
//					recipe = new PumpkinSpiceDecorator(recipe);
//					break;
//					
//				case "Nutmeg":
//					recipe = new NutmegDecorator(recipe);
//					break;
//				}
//				
//				
//			}
//			
//		}
		return recipe;
	}

}
