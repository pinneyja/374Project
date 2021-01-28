package BusinessLayer;

import DataLayer.CoffeeMachine;
import ServiceLayer.Order;

import java.util.ArrayList;

public class BuildAdvancedCommand implements BuildCommandBehavior {
    @Override
    public Command buildCommand(Order order, CoffeeMachine coffeeMachine) {
        int coffeeMachineID = coffeeMachine.getMachineId();
        int controllerID = coffeeMachine.getControllerID();
        int orderID = order.getOrderID();
        String drinkName = order.getDrinkName();
        ArrayList<Option> options = order.getOptions();

        return new Command(controllerID, coffeeMachineID, orderID, drinkName, BuildCommandBehavior.REQUEST_TYPE_AUTOMATED, options);
    }
}
