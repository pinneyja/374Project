package BusinessLayer.InterLayerCommunication;

import BusinessLayer.RecipeCreation.RecipeStep;

import java.util.ArrayList;

public class Command {
    private int controllerID, coffeeMachineID, orderID;
    private String drinkName, requestType;
    private ArrayList<Option> options;
    private ArrayList<RecipeStep> recipeSteps;

    public Command(int controllerID, int coffeeMachineID, int orderID, String drinkName, String requestType, ArrayList<Option> options) {
        this.controllerID = controllerID;
        this.coffeeMachineID = coffeeMachineID;
        this.orderID = orderID;
        this.drinkName = drinkName;
        this.requestType = requestType;
        this.options = options;
    }

    public Command(int controllerID, int coffeeMachineID, int orderID, String drinkName,
                   String requestType, ArrayList<Option> options, ArrayList<RecipeStep> recipeSteps) {
        this(controllerID, coffeeMachineID, orderID, drinkName, requestType, options);
        this.recipeSteps = recipeSteps;

    }

    public int getControllerID() {
        return controllerID;
    }

    public int getCoffeeMachineID() {
        return coffeeMachineID;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getRequestType() {
        return requestType;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public ArrayList<RecipeStep> getRecipe() {
        return recipeSteps;
    }
}
