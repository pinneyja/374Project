package BusinessLayer;

import DataLayer.ControllerResponse;

public interface Observer {
    void update(ControllerResponse controllerResponse);
}
