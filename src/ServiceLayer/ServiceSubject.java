package ServiceLayer;

import BusinessLayer.InterLayerCommunication.ServiceObserver;

/**
 * Provides an interface for an object that can be observed based on the Observer Design Pattern.
 */
public interface ServiceSubject {
    void registerObserver(ServiceObserver serviceObserver);
    void removeObserver(ServiceObserver serviceObserver);
    void notifyObservers(Order order);
}