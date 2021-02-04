package BusinessLayer.CoffeeMakers;

import BusinessLayer.CommandBehaviors.BuildAdvancedCommand;

public class AdvancedCoffeeMaker extends CoffeeMaker {
    public AdvancedCoffeeMaker() {
        this.buildCommandBehavior = new BuildAdvancedCommand();
    }
}
