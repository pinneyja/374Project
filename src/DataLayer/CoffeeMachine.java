package DataLayer;

public class CoffeeMachine {
    private int machineId;
    private int controllerID;
    private String type;

    public CoffeeMachine(int machineID, int controllerID, String type) {
        this.machineId = machineID;
        this.controllerID = controllerID;
        this.type = type;
    }

    public int getMachineId() {
        return this.machineId;
    }

    public int getControllerID() {
        return this.controllerID;
    }

    public String getTypeOfMachine() {
        return this.type;
    }
}