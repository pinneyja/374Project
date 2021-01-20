package DataLayer;

public class ControllerResponse {
    private int orderID, status;
    private Integer errorCode; // Integer instead of int so that it is nullable (not all responses will have an error)
    private String errorDescription;

    public ControllerResponse(int orderID, int status, Integer errorCode, String errorDescription) {
        this.orderID = orderID;
        this.status = status;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getStatus() {
        return status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
