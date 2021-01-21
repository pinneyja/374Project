package BusinessLayer;

import ServiceLayer.Order;

public interface BuildCommandBehavior {
    Command buildCommand(Order order);
}
