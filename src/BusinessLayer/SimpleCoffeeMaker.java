package BusinessLayer;

public class SimpleCoffeeMaker extends CoffeeMaker {
    public SimpleCoffeeMaker() {
        this.buildCommandBehavior = new BuildSimpleCommand();
    }

    public void setBuildCommandBehavior(BuildCommandBehavior buildCommandBehavior) {
        this.buildCommandBehavior = buildCommandBehavior;
    }
}
