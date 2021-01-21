package BusinessLayer;

import ServiceLayer.ApplicationInterface;

public class AdvancedCoffeeMaker extends CoffeeMaker {
    public AdvancedCoffeeMaker(ApplicationInterface applicationInterface) {
        super(applicationInterface);

        this.buildCommandBehavior = new BuildAdvancedCommand();
        this.applicationInterface.getEventChannel().registerAdvancedSubscriber(this);
    }
}
