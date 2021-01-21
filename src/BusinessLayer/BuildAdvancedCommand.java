package BusinessLayer;

import ServiceLayer.Order;
import java.util.ArrayList;

public class BuildAdvancedCommand implements BuildCommandBehavior{
    @Override
    public Command buildCommand(Order order) {
        System.out.println("Building advanced command!");
        int coffeeMachineID = 0; // TODO: get list of coffee machines from DB for this address, make sure they are simple, then choose random
        int controllerID = 0; // TODO: get controller ID for that coffee machine

        int orderID = order.getOrderID();
        String drinkName = order.getDrinkName();
        ArrayList<Option> options = order.getOptions();

        return new Command(controllerID, coffeeMachineID, orderID, drinkName, BuildCommandBehavior.REQUEST_TYPE_AUTOMATED, options);
    }
}
