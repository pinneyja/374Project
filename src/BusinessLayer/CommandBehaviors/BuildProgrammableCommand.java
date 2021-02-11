package BusinessLayer.CommandBehaviors;

import BusinessLayer.InterLayerCommunication.Command;
import BusinessLayer.InterLayerCommunication.Option;
import BusinessLayer.RecipeCreation.ConcreteRecipeCreator;
import BusinessLayer.RecipeCreation.Recipe;
import BusinessLayer.RecipeCreation.RecipeCreator;
import BusinessLayer.RecipeCreation.RecipeStep;
import Commander.BuildRecipeCommand;
import Commander.Commander;
import DataLayer.CoffeeMachine;
import ServiceLayer.Order;

import java.util.ArrayList;

public class BuildProgrammableCommand implements BuildCommandBehavior {
    @Override
    public Command buildCommand(Order order, CoffeeMachine coffeeMachine) {
        ArrayList<Option> ingredients = new ArrayList<>();
        String drinkType = order.getDrinkName();

        int coffeeMachineID = coffeeMachine.getMachineId();
        int controllerID = coffeeMachine.getControllerID();
        int orderID = order.getOrderID();
        String drinkName = order.getDrinkName();
        ArrayList<Option> options = order.getOptions();

        ArrayList<RecipeStep> recipeSteps = null;
        if(options != null && options.size() > 0) {
            for (Option option : order.getOptions()) {
                if (option.isIngredient()) {
                    ingredients.add(option);
                }
            }

            if (ingredients.size() > 0) {
                RecipeCreator recipeCreator = new ConcreteRecipeCreator();
                Recipe recipe = recipeCreator.createRecipe(drinkType, ingredients);
                
                //Command for building a recipe passed the recipe 
                BuildRecipeCommand buildRecipeCommand = new BuildRecipeCommand(recipe);
                
                //execute the command
                buildRecipeCommand.execute();
                recipeSteps = (recipe == null) ? null : buildRecipeCommand.getExecute(); //get the execution result of the command
                
                
                //Singleton idea for buildrecipe ***

            }
            options.removeAll(ingredients);
        }


        return new Command(controllerID, coffeeMachineID, orderID, drinkName, BuildCommandBehavior.REQUEST_TYPE_PROGRAMMABLE, options, recipeSteps);
    }
}
