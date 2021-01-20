package BusinessLayer;

public abstract class CoffeeMaker implements Observer, Subscriber {
    BuildCommandBehavior buildCommandBehavior;

    public void buildCommand() {
        this.buildCommandBehavior.buildCommand();
    }

    public abstract void buildAppResponse();

}