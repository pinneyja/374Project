package BusinessLayer.InterLayerCommunication;

import ServiceLayer.Order;

public interface ServiceObserver {
    void update(Order order);
}
