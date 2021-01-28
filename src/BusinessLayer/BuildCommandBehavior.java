package BusinessLayer;

import DataLayer.CoffeeMachine;
import ServiceLayer.Order;

public interface BuildCommandBehavior {
    String REQUEST_TYPE_SIMPLE = "Simple";
    String REQUEST_TYPE_AUTOMATED = "Automated";

    Command buildCommand(Order order, CoffeeMachine coffeeMachine);
}
