package BusinessLayer;

import ServiceLayer.ApplicationInterface;

public class AdvancedCoffeeMaker extends CoffeeMaker {
    public AdvancedCoffeeMaker() {
        this.buildCommandBehavior = new BuildAdvancedCommand();
    }

    public void setBuildCommandBehavior(BuildCommandBehavior buildCommandBehavior) {
        this.buildCommandBehavior = buildCommandBehavior;
    }
}
