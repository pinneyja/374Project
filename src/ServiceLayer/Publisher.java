package ServiceLayer;

public abstract class Publisher {
    protected EventChannel eventChannel;

    abstract void publishEvent(Order order);
}