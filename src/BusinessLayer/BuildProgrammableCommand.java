package BusinessLayer;

import DataLayer.CoffeeMachine;
import ServiceLayer.Order;

import java.util.ArrayList;

public class BuildProgrammableCommand implements BuildCommandBehavior {
    @Override
    public Command buildCommand(Order order, CoffeeMachine coffeeMachine) {
        ArrayList<Option> ingredients = new ArrayList<>();
        String drinkType = order.getDrinkName();

        for(Option option : order.getOptions()) {
            if(option.isIngredient()) {
                ingredients.add(option);
            }
        }

        // TODO, create a Recipe creator and call createRecipe(drinkType, ingredients)
        ArrayList<RecipeStep> recipeSteps =

        int coffeeMachineID = coffeeMachine.getMachineId();
        int controllerID = coffeeMachine.getControllerID();
        int orderID = order.getOrderID();
        String drinkName = order.getDrinkName();
        ArrayList<Option> options = order.getOptions();

        return new Command(controllerID, coffeeMachineID, orderID, drinkName, BuildCommandBehavior.REQUEST_TYPE_AUTOMATED, options, recipeSteps);
    }
}
