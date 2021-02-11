package BusinessLayer.CoffeeMakers;

import BusinessLayer.CommandBehaviors.BuildProgrammableCommand;

/**
 * This class represents the class of Programmable Coffee Makers and builds commands up to the programmable level.
 */
public class ProgrammableCoffeeMaker extends CoffeeMaker {
    public ProgrammableCoffeeMaker() {
        setBuildCommandBehavior(new BuildProgrammableCommand());
    }
}
