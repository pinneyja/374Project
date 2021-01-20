package ServiceLayer;

public class ApplicationInterface extends Publisher {

    public ApplicationInterface() {
        this.eventChannel = new EventChannel();
    }

    @Override
    void publishEvent() {

    }

    public EventChannel getEventChannel() {
        return this.eventChannel;
    }
}