package BusinessLayer;

public class AdvancedCoffeeMaker extends CoffeeMaker {
    public AdvancedCoffeeMaker() {
        this.buildCommandBehavior = new BuildAdvancedCommand();
    }
}
