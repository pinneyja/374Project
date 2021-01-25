package BusinessLayer;

import DataLayer.ControllerResponse;

public interface DataObserver {
    void update(ControllerResponse controllerResponse);
}
