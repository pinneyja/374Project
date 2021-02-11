package BusinessLayer.CoffeeMakers;

import BusinessLayer.CommandBehaviors.BuildSimpleCommand;

/**
 * This class represents the class of Simple Coffee Makers and builds simple commands.
 */
public class SimpleCoffeeMaker extends CoffeeMaker {
    public SimpleCoffeeMaker() {
        setBuildCommandBehavior(new BuildSimpleCommand());
    }
}
