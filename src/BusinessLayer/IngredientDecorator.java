package BusinessLayer;

import java.util.ArrayList;

public interface IngredientDecorator extends Recipe {
	ArrayList<RecipeStep> buildRecipe();
}
