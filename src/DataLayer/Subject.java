package DataLayer;

import BusinessLayer.CoffeeMaker;

public interface Subject {
	
	public void registerObserver(CoffeeMaker cM);
	public void removeObserver(CoffeeMaker cM);
	public void notifyObservers(ControllerResponse response);
	
}
