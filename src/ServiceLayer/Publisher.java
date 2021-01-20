package ServiceLayer;

public abstract class Publisher {
    private EventChannel eventChannel;

    abstract void publishEvent();
}