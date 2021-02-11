package BusinessLayer.InterLayerCommunication;

/**
 * Data object that represents the app's response to a given order.
 */
public class AppResponse {
    private int orderID, coffeeMachineID, status;
    private String statusMessage, errorMessage;

    public AppResponse(int orderID, int coffeeMachineID, int status, String statusMessage, String errorMessage) {
        this.orderID = orderID;
        this.coffeeMachineID = coffeeMachineID;
        this.status = status;
        this.statusMessage = statusMessage;
        this.errorMessage = errorMessage;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getCoffeeMachineID() {
        return coffeeMachineID;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
