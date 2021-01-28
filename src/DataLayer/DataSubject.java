package DataLayer;

import BusinessLayer.DataObserver;

public interface DataSubject {
    void registerObserver(DataObserver dataObserver);

    void removeObserver(DataObserver dataObserver);

    void notifyObservers(ControllerResponse response);

}
