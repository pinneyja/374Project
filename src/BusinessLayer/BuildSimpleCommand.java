package BusinessLayer;

import DataLayer.CoffeeMachine;
import DataLayer.DatabaseConnection;
import ServiceLayer.Order;
import java.util.ArrayList;
import java.util.Random;

public class BuildSimpleCommand implements BuildCommandBehavior {
    @Override
    public Command buildCommand(Order order, CoffeeMachine coffeeMachine) {
            int coffeeMachineID = coffeeMachine.getMachineId();
            int controllerID = coffeeMachine.getControllerID();
            int orderID = order.getOrderID();
            String drinkName = order.getDrinkName();
            ArrayList<Option> options = new ArrayList<>();

            return new Command(controllerID, coffeeMachineID, orderID, drinkName, BuildCommandBehavior.REQUEST_TYPE_SIMPLE, options);
    }
}
