package BusinessLayer;

import java.util.ArrayList;

public class Command {
    private int controllerID, coffeeMachineID, orderID;
    private String drinkName, requestType;
    private ArrayList<Option> options;

    public Command(int controllerID, int coffeeMachineID, int orderID, String drinkName, String requestType, ArrayList<Option> options) {
        this.controllerID = controllerID;
        this.coffeeMachineID = coffeeMachineID;
        this.orderID = orderID;
        this.drinkName = drinkName;
        this.requestType = requestType;
        this.options = options;
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
}
