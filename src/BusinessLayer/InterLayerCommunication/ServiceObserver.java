package BusinessLayer.InterLayerCommunication;

import ServiceLayer.Order;

/**
 * Provides an interface to observe new orders from the Service Layer.
 */
public interface ServiceObserver {
    void update(Order order);
}
