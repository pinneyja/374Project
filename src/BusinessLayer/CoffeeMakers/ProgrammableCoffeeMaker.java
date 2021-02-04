package BusinessLayer.CoffeeMakers;

import BusinessLayer.CommandBehaviors.BuildProgrammableCommand;

public class ProgrammableCoffeeMaker extends CoffeeMaker {
    public ProgrammableCoffeeMaker() {
        this.buildCommandBehavior = new BuildProgrammableCommand();
    }
}
