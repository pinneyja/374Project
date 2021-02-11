package BusinessLayer.CommandBehaviors;

import BusinessLayer.InterLayerCommunication.Command;
import BusinessLayer.InterLayerCommunication.Option;
import DataLayer.CoffeeMachine;
import ServiceLayer.Order;

import java.util.ArrayList;

public class BuildAdvancedCommand implements BuildCommandBehavior {

    /**
     * Builds an advanced/automated or simple command for a given Order.
     *
     * @param order
     * @param coffeeMachine
     * @return
     */
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
