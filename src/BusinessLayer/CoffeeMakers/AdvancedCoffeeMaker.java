package BusinessLayer.CoffeeMakers;

import BusinessLayer.CommandBehaviors.BuildAdvancedCommand;

/**
 * This class represents the class of Advanced/Automated Coffee Makers and builds commands up to the Advanced/Automated
 * level.
 */
public class AdvancedCoffeeMaker extends CoffeeMaker {
    public AdvancedCoffeeMaker() {
        this.buildCommandBehavior = new BuildAdvancedCommand();
    }
}
