package BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;

public interface RecipeCreator {
	
	Recipe createRecipe(String drinkType, ArrayList<Option> ingredients);

}
