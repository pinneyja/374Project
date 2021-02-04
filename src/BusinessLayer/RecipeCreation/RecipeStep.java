package BusinessLayer.RecipeCreation;

import java.util.Collection;

public class RecipeStep {
	
	String commandStep;
	String object;
	
	public RecipeStep(String commandStep, String object)
	{
		this.commandStep = commandStep;
		this.object = object;
		
	}

	public String getCommandStep() {
		return commandStep;
	}

	public String getObject() {
		return object;
	}
	

}
