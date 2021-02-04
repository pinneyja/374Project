package BusinessLayer.CoffeeMakers;

import BusinessLayer.CommandBehaviors.BuildSimpleCommand;

public class SimpleCoffeeMaker extends CoffeeMaker {
    public SimpleCoffeeMaker() {
        this.buildCommandBehavior = new BuildSimpleCommand();
    }
}
