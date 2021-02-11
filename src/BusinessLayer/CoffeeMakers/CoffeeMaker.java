package BusinessLayer.CoffeeMakers;

import BusinessLayer.CommandBehaviors.BuildCommandBehavior;
import BusinessLayer.InterLayerCommunication.Command;
import DataLayer.CoffeeMachine;
import ServiceLayer.Order;

/**
 * This abstract class provides the structure for a subclass that represents all coffee makers of a given type. This
 * class is based on the Strategy Design Pattern.
 */
public abstract class CoffeeMaker {
    // This is the strategy to be swapped.
    protected BuildCommandBehavior buildCommandBehavior;

    /**
     * This method should build a command based on the build command behavior strategy.
     *
     * @param order
     * @param coffeeMachine
     * @return
     */
    public Command buildCommand(Order order, CoffeeMachine coffeeMachine) {
        return buildCommandBehavior.buildCommand(order, coffeeMachine);
    }

    /**
     * This method allows for the change of strategy.
     *
     * @param buildCommandBehavior
     */
    public void setBuildCommandBehavior(BuildCommandBehavior buildCommandBehavior) {
        this.buildCommandBehavior = buildCommandBehavior;
    }
}