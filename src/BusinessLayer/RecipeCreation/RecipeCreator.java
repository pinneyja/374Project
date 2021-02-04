package BusinessLayer.RecipeCreation;

import BusinessLayer.InterLayerCommunication.Option;

import java.util.ArrayList;

public interface RecipeCreator {
	
	Recipe createRecipe(String drinkType, ArrayList<Option> ingredients);

}
