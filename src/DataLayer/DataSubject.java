package DataLayer;

import BusinessLayer.DataObserver;

public interface DataSubject {
	
	public void registerObserver(DataObserver dataObserver);
	public void removeObserver(DataObserver dataObserver);
	public void notifyObservers(ControllerResponse response);
	
}
