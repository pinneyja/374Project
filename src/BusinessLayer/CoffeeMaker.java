package BusinessLayer;

import DataLayer.ControllerInterface;
import DataLayer.ControllerResponse;
import ServiceLayer.ApplicationInterface;
import ServiceLayer.Order;

import java.util.HashMap;

public abstract class CoffeeMaker implements Observer, Subscriber {
    protected BuildCommandBehavior buildCommandBehavior;
    protected HashMap<Integer, Integer> orderIDtoCoffeeMachineID;
    private HashMap<Integer, String> statusToMessage;
    protected ApplicationInterface applicationInterface;
    private ControllerInterface controllerInterface;

    public CoffeeMaker(ApplicationInterface applicationInterface) {
        this.applicationInterface = applicationInterface;
        orderIDtoCoffeeMachineID = new HashMap<>();

        statusToMessage = new HashMap<>();
        statusToMessage.put(0, "Your coffee has been prepared with your desired options.");
        statusToMessage.put(1, "Your coffee order has been cancelled.");

        controllerInterface = new ControllerInterface();
    }

    public Command buildCommand(Order order) {
        Command command = this.buildCommandBehavior.buildCommand(order);
        this.orderIDtoCoffeeMachineID.put(order.getOrderID(), command.getCoffeeMachineID());

        return command;
    }

    public AppResponse buildAppResponse(ControllerResponse controllerResponse) {
        int orderID = controllerResponse.getOrderID();
        if(orderIDtoCoffeeMachineID.containsKey(orderID)) {
            int status = controllerResponse.getStatus();
            int coffeeMachineID = orderIDtoCoffeeMachineID.get(orderID);
            String errorDescription = controllerResponse.getErrorDescription();
            String statusMessage = statusToMessage.getOrDefault(status, "Unknown status.");

            return new AppResponse(orderID, coffeeMachineID, status, statusMessage, errorDescription);
        } else {
            return null;
        }
    }

    public void update(ControllerResponse controllerResponse) {
        AppResponse appResponse = buildAppResponse(controllerResponse);

        if(appResponse != null) {
            applicationInterface.returnAppResponse(appResponse);
        }
    }

    public void update(Order order) {
        Command command = buildCommand(order);
        controllerInterface.receiveCommand(command);
    }

}