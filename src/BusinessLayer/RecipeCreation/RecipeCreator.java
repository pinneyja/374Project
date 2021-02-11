package BusinessLayer.RecipeCreation;

import BusinessLayer.InterLayerCommunication.Option;

import java.util.ArrayList;

//Recipe Creator interface for the factory method design pattern (uses create recipe)
public interface RecipeCreator {
	
	Recipe createRecipe(String drinkType, ArrayList<Option> ingredients);

}
