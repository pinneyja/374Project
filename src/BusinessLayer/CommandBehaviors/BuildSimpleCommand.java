package BusinessLayer.CommandBehaviors;

import BusinessLayer.InterLayerCommunication.Command;
import DataLayer.CoffeeMachine;
import ServiceLayer.Order;

public class BuildSimpleCommand implements BuildCommandBehavior {

    /**
     * Builds a simple command based on a given order.
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

        return new Command(controllerID, coffeeMachineID, orderID, drinkName, BuildCommandBehavior.REQUEST_TYPE_SIMPLE, null);
    }
}
