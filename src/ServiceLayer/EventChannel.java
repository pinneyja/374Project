package ServiceLayer;

import BusinessLayer.Subscriber;
import java.util.HashSet;

public class EventChannel {

    HashSet<Subscriber> simpleSubscribers;
    HashSet<Subscriber> advancedSubscribers;

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
            for(Subscriber subscriber : advancedSubscribers) {
                subscriber.update(order);
            }
        } else {
            for(Subscriber subscriber : simpleSubscribers) {
                subscriber.update(order);
            }
        }
    }
}
