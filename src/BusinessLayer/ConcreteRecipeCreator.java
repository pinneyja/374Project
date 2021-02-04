package BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class ConcreteRecipeCreator implements RecipeCreator{

	@Override
	public Recipe createRecipe(String drinkType, ArrayList<Option> ingredients) {
		// TODO Auto-generated method stub
		
		if  (drinkType.equals("Latte"))
		{
			Recipe latteRecipe = new LatteRecipe();
			
			return decorate(latteRecipe, ingredients);
		}
		
		else if (drinkType.equals("Pumpkin Spice")) 
		{
			Recipe pumpkinSpiceRecipe = new PumpkinSpiceRecipe();
			
			return decorate(pumpkinSpiceRecipe, ingredients);


		}
		
		
		return null;
		
		
	}
	
	
	public Recipe decorate(Recipe recipe, ArrayList<Option> ingredients) 
	{
		
		for (Option option : ingredients)
		{
			String optionName = option.getName();
			int optionQuantity = option.getQuantity();
			
			for(int i = 0; i < optionQuantity; i++)
			{
				switch (optionName) 
				{
				case "coffee":
					recipe = new CoffeeDecorator(recipe);
					break;
				
				case "milk":
					recipe = new MilkDecorator(recipe);
					break;
					
				case "soymilk":
					recipe = new SoyMilkDecorator(recipe);
					break;
					
				case "sugar":
					recipe = new SugarDecorator(recipe);
					break;
					
				case "decaff coffee":
					recipe = new DecaffCoffeeDecorator(recipe);
					break;
					
				case "whipped cream":
					recipe = new WhippedCreamDecorator(recipe);
					break;
					
				case "hazelnut":
					recipe = new HazelnutDecorator(recipe);
					break;
					
				case "pumpkin spice":
					recipe = new PumpkinSpiceDecorator(recipe);
					break;
					
				case "nutmeg":
					recipe = new NutmegDecorator(recipe);
					break;
				}
				
				
			}
			
		}
		return recipe;
	}

}
