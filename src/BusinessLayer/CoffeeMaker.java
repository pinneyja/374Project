package BusinessLayer;

import DataLayer.ControllerResponse;
import ServiceLayer.ApplicationInterface;

import java.util.HashMap;

public abstract class CoffeeMaker implements Observer, Subscriber {
    protected BuildCommandBehavior buildCommandBehavior;
    protected HashMap<Integer, Integer> orderIDtoCoffeeMachineID;
    private HashMap<Integer, String> statusToMessage;
    private ApplicationInterface applicationInterface;

    public CoffeeMaker() {
        statusToMessage = new HashMap<>();
        statusToMessage.put(0, "Your coffee has been prepared with your desired options.");
        statusToMessage.put(1, "Your coffee order has been cancelled.");
    }

    public Command buildCommand() {
        return this.buildCommandBehavior.buildCommand();
    }

    public void buildAppResponse(ControllerResponse controllerResponse) {
        int orderID = controllerResponse.getOrderID();
        if(orderIDtoCoffeeMachineID.containsKey(orderID)) {
            int status = controllerResponse.getStatus();
            int coffeeMachineID = orderIDtoCoffeeMachineID.get(orderID);
            String errorDescription = controllerResponse.getErrorDescription();
            String statusMessage = statusToMessage.getOrDefault(status, "Unknown status.");

            AppResponse appResponse = new AppResponse(orderID, coffeeMachineID, status, statusMessage, errorDescription);
            applicationInterface.returnAppResponse(appResponse);
        }
    }

}