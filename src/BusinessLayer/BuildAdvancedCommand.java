package BusinessLayer;

import DataLayer.CoffeeMachine;
import DataLayer.DatabaseConnection;
import ServiceLayer.Order;
import java.util.ArrayList;
import java.util.Random;

public class BuildAdvancedCommand implements BuildCommandBehavior{
    @Override
    public Command buildCommand(Order order, CoffeeMachine coffeeMachine) {
        System.out.println("Building advanced command!");

        int coffeeMachineID = coffeeMachine.getMachineId();
        int controllerID = coffeeMachine.getControllerID();
        int orderID = order.getOrderID();
        String drinkName = order.getDrinkName();
        ArrayList<Option> options = order.getOptions();

        return new Command(controllerID, coffeeMachineID, orderID, drinkName, BuildCommandBehavior.REQUEST_TYPE_AUTOMATED, options);
    }
}
