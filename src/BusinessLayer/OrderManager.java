package BusinessLayer;

import DataLayer.ControllerInterface;
import DataLayer.ControllerResponse;
import DataLayer.DatabaseConnection;
import ServiceLayer.ApplicationInterface;
import ServiceLayer.Order;

import java.util.HashMap;

public class OrderManager implements DataObserver, ServiceObserver{
    DatabaseConnection databaseConnection;
    CoffeeMaker simpleCoffeeMaker;
    CoffeeMaker advancedCoffeeMaker;
    ApplicationInterface applicationInterface;
    ControllerInterface controllerInterface;

    private HashMap<Integer, Integer> orderIDtoCoffeeMachineID;
    private HashMap<Integer, String> statusToMessage;

    public OrderManager(ApplicationInterface applicationInterface) {
        this.applicationInterface = applicationInterface;
        applicationInterface.registerObserver(this);

        controllerInterface = new ControllerInterface();
        controllerInterface.registerObserver(this);

        databaseConnection = new DatabaseConnection();
        simpleCoffeeMaker = new SimpleCoffeeMaker();
        advancedCoffeeMaker = new AdvancedCoffeeMaker();
        orderIDtoCoffeeMachineID = new HashMap<>();

        statusToMessage = new HashMap<>();
        statusToMessage.put(0, "Your coffee has been prepared with your desired options.");
        statusToMessage.put(1, "Your coffee order has been cancelled.");
    }

    @Override
    public void update(ControllerResponse controllerResponse) {
        AppResponse appResponse = buildAppResponse(controllerResponse);

        if(appResponse != null) {
            applicationInterface.returnAppResponse(appResponse);
        }
    }

    @Override
    public void update(Order order) {
        Command command = buildCommand(order);

        if(command.getCoffeeMachineID() < 0 || command.getControllerID() < 0) {
            update(new ControllerResponse(order.getOrderID(), 1, -1, "No machines available."));
        } else {
            controllerInterface.receiveCommand(command);
        }
    }

    public Command buildCommand(Order order) {
        // TODO implement logic to determine which coffee maker should build the order, then use it to build the order
        return null;
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
}
