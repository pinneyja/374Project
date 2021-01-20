package ServiceLayer;

import BusinessLayer.Subscriber;
import java.util.HashSet;

public class EventChannel {
    private HashSet<Subscriber> simpleSubscribers;
    private HashSet<Subscriber> advancedSubscribers;

    public EventChannel() {
        simpleSubscribers = new HashSet<>();
        advancedSubscribers = new HashSet<>();
    }

    public void registerSimpleSubscriber(Subscriber subscriber) {
        simpleSubscribers.add(subscriber);
    }

    public void registerAdvancedSubscriber(Subscriber subscriber) {
        advancedSubscribers.add(subscriber);
    }

    public void removeSimpleSubscriber(Subscriber subscriber) {
        simpleSubscribers.remove(subscriber);
    }

    public void removeAdvancedSubscriber(Subscriber subscriber) {
        advancedSubscribers.remove(subscriber);
    }

    public void notifySubscribers(Order order) {
        // TODO: Potentially check order drink vs what a simple coffee machine can do
        if(order.options != null && order.options.size() > 0) {
            System.out.println("Firing notifications for advanced order!");
            for(Subscriber subscriber : advancedSubscribers) {
                subscriber.update(order);
            }
        } else {
            System.out.println("Firing notifications for simple order!");
            for(Subscriber subscriber : simpleSubscribers) {
                subscriber.update(order);
            }
        }
    }
}
