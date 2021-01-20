package BusinessLayer;

import DataLayer.ControllerResponse;

public interface Observer {
    public void update(ControllerResponse controllerResponse);
}
