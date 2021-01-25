package BusinessLayer;

import ServiceLayer.Order;

public abstract class CoffeeMaker {
    protected BuildCommandBehavior buildCommandBehavior;

    public Command buildCommand(Order order) {
        return buildCommandBehavior.buildCommand(order);
    }
}