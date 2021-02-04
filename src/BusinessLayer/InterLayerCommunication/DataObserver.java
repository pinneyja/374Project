package BusinessLayer.InterLayerCommunication;

import DataLayer.ControllerResponse;

public interface DataObserver {
    void update(ControllerResponse controllerResponse);
}
