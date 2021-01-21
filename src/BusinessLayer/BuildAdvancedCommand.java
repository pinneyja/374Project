package BusinessLayer;

import DataLayer.CoffeeMachine;
import DataLayer.DatabaseConnection;
import ServiceLayer.Order;
import java.util.ArrayList;
import java.util.Random;

public class BuildAdvancedCommand implements BuildCommandBehavior{
    private DatabaseConnection databaseConnection;
    private Random random;

    public BuildAdvancedCommand() {
        this.databaseConnection = new DatabaseConnection();
        this.random = new Random();
    }

    @Override
    public Command buildCommand(Order order) {
        System.out.println("Building advanced command!");
        ArrayList<CoffeeMachine> coffeeMachines = databaseConnection.getCoffeeMachinesAtAddress(order.getStreetAddress(), order.getZipCode());
        ArrayList<CoffeeMachine> advancedMachines = new ArrayList<>();

        for(CoffeeMachine coffeeMachine : coffeeMachines) {
            if(coffeeMachine.getTypeOfMachine().equals("Advanced")) {
                   advancedMachines.add(coffeeMachine);
            }
        }

        if(advancedMachines.size() > 0) {
            CoffeeMachine coffeeMachine = advancedMachines.get(random.nextInt(coffeeMachines.size()));

            int coffeeMachineID = coffeeMachine.getMachineId();
            int controllerID = coffeeMachine.getControllerID();
            int orderID = order.getOrderID();
            String drinkName = order.getDrinkName();
            ArrayList<Option> options = order.getOptions();

            return new Command(controllerID, coffeeMachineID, orderID, drinkName, BuildCommandBehavior.REQUEST_TYPE_AUTOMATED, options);
        } else {
            return new Command(-1, -1, order.getOrderID(), order.getDrinkName(), BuildCommandBehavior.REQUEST_TYPE_AUTOMATED, order.getOptions());
        }
    }
}
