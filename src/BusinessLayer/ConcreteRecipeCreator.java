package BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class ConcreteRecipeCreator implements RecipeCreator{

	@Override
	public Recipe createRecipe(String drinkType, ArrayList<Option> ingredients) {
		// TODO Auto-generated method stub
		
		if  (drinkType.equals("Latte"))
		{
			Recipe latRec = new LatteRecipe();
		
//			return;
		}
		
		else if (drinkType.equals("PumpkinSpice")) //change if it needs to be different
		{
			Recipe pumpRec = new PumpkinSpiceRecipe();
			
//			return;
		}
		
		
		return null;
		
		
	}

}
