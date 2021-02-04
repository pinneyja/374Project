package BusinessLayer;

public class ProgrammableCoffeeMaker extends CoffeeMaker {
    public ProgrammableCoffeeMaker() {
        this.buildCommandBehavior = new BuildProgrammableCommand();
    }
}
