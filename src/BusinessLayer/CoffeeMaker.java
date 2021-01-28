package BusinessLayer;

import DataLayer.CoffeeMachine;
import ServiceLayer.Order;

public abstract class CoffeeMaker {
    protected BuildCommandBehavior buildCommandBehavior;

    public Command buildCommand(Order order, CoffeeMachine coffeeMachine) {
        return buildCommandBehavior.buildCommand(order, coffeeMachine);
    }

    public void setBuildCommandBehavior(BuildCommandBehavior buildCommandBehavior) {
        this.buildCommandBehavior = buildCommandBehavior;
    }
}