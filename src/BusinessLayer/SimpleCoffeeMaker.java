package BusinessLayer;

import ServiceLayer.ApplicationInterface;

public class SimpleCoffeeMaker extends CoffeeMaker {
    public SimpleCoffeeMaker(ApplicationInterface applicationInterface) {
        super(applicationInterface);

        this.buildCommandBehavior = new BuildSimpleCommand();
        this.applicationInterface.getEventChannel().registerSimpleSubscriber(this);
    }
}
