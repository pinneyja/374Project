package BusinessLayer.InterLayerCommunication;

import DataLayer.ControllerResponse;

/**
 * Provides an interface to observe controller responses from the DataLayer.
 */
public interface DataObserver {
    void update(ControllerResponse controllerResponse);
}
