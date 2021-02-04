package BusinessLayer;

import DataLayer.CoffeeMachine;
import ServiceLayer.Order;

public interface BuildCommandBehavior {
    String REQUEST_TYPE_SIMPLE = "Simple";
    String REQUEST_TYPE_AUTOMATED = "Automated";
    String REQUEST_TYPE_PROGRAMMABLE = "Programmable";

    Command buildCommand(Order order, CoffeeMachine coffeeMachine);
}
