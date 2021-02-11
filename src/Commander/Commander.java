package Commander;

import java.util.ArrayList;

import BusinessLayer.RecipeCreation.RecipeStep;

//Interface for the Command Pattern
public interface Commander {
	
	//All Concrete Commanders will need to execute
	public void execute();
	

}
