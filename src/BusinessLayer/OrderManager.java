package BusinessLayer;

import BusinessLayer.CoffeeMakers.AdvancedCoffeeMaker;
import BusinessLayer.CoffeeMakers.CoffeeMaker;
import BusinessLayer.CoffeeMakers.ProgrammableCoffeeMaker;
import BusinessLayer.CoffeeMakers.SimpleCoffeeMaker;
import BusinessLayer.InterLayerCommunication.*;
import DataLayer.CoffeeMachine;
import DataLayer.ControllerInterface;
import DataLayer.ControllerResponse;
import DataLayer.DatabaseConnection;
import ServiceLayer.ApplicationInterface;
import ServiceLayer.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class OrderManager implements DataObserver, ServiceObserver {
    DatabaseConnection databaseConnection;
    CoffeeMaker simpleCoffeeMaker;
    CoffeeMaker advancedCoffeeMaker;
    CoffeeMaker programmableCoffeeMaker;
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
        programmableCoffeeMaker = new ProgrammableCoffeeMaker();
        orderIDtoCoffeeMachineID = new HashMap<>();

        statusToMessage = new HashMap<>();
        statusToMessage.put(-1, "There are no available coffee machines to place your order.");
        statusToMessage.put(0, "Your coffee has been prepared with your desired options.");
        statusToMessage.put(1, "Your coffee order has been cancelled.");
        statusToMessage.put(2, "Your order timed out.");
    }

    @Override
    public void update(ControllerResponse controllerResponse) {
        AppResponse appResponse = buildAppResponse(controllerResponse);

        if (appResponse != null) {
            applicationInterface.returnAppResponse(appResponse);
        }
    }

    @Override
    public void update(final Order order) {
        final Command command = buildCommand(order);

        final Runnable recComm = new Thread() {
            @Override
            public void run() {
                controllerInterface.sendCommand(command);
            }
        };
        final Runnable upComm = new Thread() {
            @Override
            public void run() {
                update(new ControllerResponse(order.getOrderID(), 1, -1, "No machines available."));

            }
        };

        final ExecutorService exe = Executors.newSingleThreadExecutor();
        final Future fut = (command.getCoffeeMachineID() < 0 || command.getControllerID() < 0) ? exe.submit(upComm) : exe.submit(recComm);

        try {
            fut.get(10, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException | TimeoutException ie) {
            update(new ControllerResponse(order.getOrderID(), 2, 5, "Order Timeout"));
            orderIDtoCoffeeMachineID.remove(order.getOrderID());
        }
    }

    public Command buildCommand(Order order) {
        ArrayList<CoffeeMachine> machines = this.databaseConnection.getCoffeeMachinesAtAddress(order.getStreetAddress(), order.getZipCode());

        CoffeeMachine primaryMach = new CoffeeMachine(-1, -1, "Simple");
        boolean orderIsSimple = order.getOptions() == null || order.getOptions().size() == 0;

        ArrayList<String> ingredientsForDrink = this.databaseConnection.getIngredients(order.getDrinkName());
        ArrayList<Option> ingredients = new ArrayList<>();
        if(order.getOptions() != null) {
            for (Option option : order.getOptions()) {
                if (ingredientsForDrink.contains(option.getName())) {
                    ingredients.add(option);
                }
            }
        }

        boolean orderIsAutomated = !orderIsSimple && ingredients.size() == 0;
        ArrayList<CoffeeMachine> machinesToRemove = new ArrayList<>();

        for (CoffeeMachine c : machines) {
            if (!this.databaseConnection.getDrinksForCoffeeMachine(c.getMachineId()).contains(order.getDrinkName()) ||
                    (!orderIsSimple && c.getTypeOfMachine().equals("Simple")) ||
                    (!orderIsSimple && !orderIsAutomated && c.getTypeOfMachine().equals("Automated"))) {
                machinesToRemove.add(c);
            }
        }

        machines.removeAll(machinesToRemove);

        if (machines.size() > 0) {
            if (!orderIsSimple) {
                primaryMach = machines.get(0);
            } else {
                boolean foundSimpleMachine = false;
                boolean foundAutomatedMachine = false;
                for (CoffeeMachine machine : machines) {
                    if (machine.getTypeOfMachine().equals("Simple")) {
                        foundSimpleMachine = true;
                        primaryMach = machine;
                        break;
                    } else if(machine.getTypeOfMachine().equals("Automated")) {
                        foundAutomatedMachine = true;
                        primaryMach = machine;
                    }
                }

                if (!foundSimpleMachine && !foundAutomatedMachine) {
                    primaryMach = machines.get(0);
                }
            }
        }

        this.orderIDtoCoffeeMachineID.put(order.getOrderID(), primaryMach.getMachineId());

        Command command;
        if (primaryMach.getTypeOfMachine().equals("Simple")) {
            command = simpleCoffeeMaker.buildCommand(order, primaryMach);
        } else if (primaryMach.getTypeOfMachine().equals("Automated")) {
            command = advancedCoffeeMaker.buildCommand(order, primaryMach);
        } else {
            command = programmableCoffeeMaker.buildCommand(order, primaryMach);
        }

        return command;
    }

    public AppResponse buildAppResponse(ControllerResponse controllerResponse) {
        int orderID = controllerResponse.getOrderID();
        if (orderIDtoCoffeeMachineID.containsKey(orderID)) {
            int status = controllerResponse.getStatus();
            int coffeeMachineID = orderIDtoCoffeeMachineID.get(orderID);
            String errorDescription = controllerResponse.getErrorDescription();
            String statusMessage = statusToMessage.getOrDefault(status, "Unknown status.");
            orderIDtoCoffeeMachineID.remove(orderID);

            return new AppResponse(orderID, coffeeMachineID, status, statusMessage, errorDescription);
        } else {
            return null;
        }
    }
}
