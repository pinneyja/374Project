package BusinessLayer;

import ServiceLayer.Order;

public interface Subscriber {
    public void update(Order order);
}
