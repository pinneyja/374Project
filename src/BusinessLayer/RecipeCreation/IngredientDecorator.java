package BusinessLayer.RecipeCreation;

import java.util.ArrayList;

//The IngredientDecorator extends recipe so that it can call buildRecipe and allows for the decoration of recipes
public interface IngredientDecorator extends Recipe {
	ArrayList<RecipeStep> buildRecipe();
}
