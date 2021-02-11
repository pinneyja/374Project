package BusinessLayer.CommandBehaviors;

import BusinessLayer.InterLayerCommunication.Command;
import DataLayer.CoffeeMachine;
import ServiceLayer.Order;

/**
 * This class offers an interface for varying strategies that build commands based on orders.
 */
public interface BuildCommandBehavior {
    String REQUEST_TYPE_SIMPLE = "Simple";
    String REQUEST_TYPE_AUTOMATED = "Automated";
    String REQUEST_TYPE_PROGRAMMABLE = "Programmable";

    Command buildCommand(Order order, CoffeeMachine coffeeMachine);
}
