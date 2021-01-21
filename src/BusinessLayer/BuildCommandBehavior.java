package BusinessLayer;

import ServiceLayer.Order;

public interface BuildCommandBehavior {
    String REQUEST_TYPE_SIMPLE = "Simple";
    String REQUEST_TYPE_AUTOMATED = "Automated";

    Command buildCommand(Order order);
}
