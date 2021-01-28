package BusinessLayer;

public class SimpleCoffeeMaker extends CoffeeMaker {
    public SimpleCoffeeMaker() {
        this.buildCommandBehavior = new BuildSimpleCommand();
    }
}
