package BusinessLayer;

import ServiceLayer.Order;

public interface Subscriber {
    void update(Order order);
}
