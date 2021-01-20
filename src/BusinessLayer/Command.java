package BusinessLayer;

public class Command {
    private int controllerID, coffeeMachineID, orderID;
    private String drinkName, requestType;

    public Command(int controllerID, int coffeeMachineID, int orderID, String drinkName, String requestType) {
        this.controllerID = controllerID;
        this.coffeeMachineID = coffeeMachineID;
        this.orderID = orderID;
        this.drinkName = drinkName;
        this.requestType = requestType;
    }

    public int getControllerID() {
        return controllerID;
    }

    public int getCoffeeMachineID() {
        return coffeeMachineID;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getRequestType() {
        return requestType;
    }
}