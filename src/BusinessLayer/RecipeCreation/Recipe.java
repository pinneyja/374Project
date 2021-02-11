package BusinessLayer.RecipeCreation;

import java.util.ArrayList;

//Recipe interface used for the factory method to create concrete recipes 
public interface Recipe {
	ArrayList<RecipeStep> buildRecipe();
}
