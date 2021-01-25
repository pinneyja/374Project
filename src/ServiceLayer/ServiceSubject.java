package ServiceLayer;

import BusinessLayer.ServiceObserver;

public interface ServiceSubject {
    void registerObserver(ServiceObserver serviceObserver);
    void removeObserver(ServiceObserver serviceObserver);
    void notifyObservers(Order order);
}