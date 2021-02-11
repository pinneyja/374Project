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

/**
 * This class is the brains of the app. It takes in Orders from the Service Layer and routes them to appropriate
 * controllers. It also receives controller responses from the Data Layer and sends them to the Service Layer as
 * App Responses.
 */
public class OrderManager implements DataObserver, ServiceObserver {
    DatabaseConnection databaseConnection;

    // All types of coffee makers
    CoffeeMaker simpleCoffeeMaker;
    CoffeeMaker advancedCoffeeMaker;
    CoffeeMaker programmableCoffeeMaker;

    // Interfaces to both layers
    ApplicationInterface applicationInterface;
    ControllerInterface controllerInterface;

    // Store where each order goes
    private HashMap<Integer, Integer> orderIDtoCoffeeMachineID;

    // Keep track of status messages
    private HashMap<Integer, String> statusToMessage;

    /**
     * Initialize objects in this constructor.
     *
     * @param applicationInterface
     */
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

    /**
     * Receives a communication from the DataLayer that says it has received a Controller Response. Turns it into
     * an App Response and sends it back to the Application Interface.
     *
     * @param controllerResponse
     */
    @Override
    public void update(ControllerResponse controllerResponse) {
        AppResponse appResponse = buildAppResponse(controllerResponse);

        if (appResponse != null) {
            applicationInterface.returnAppResponse(appResponse);
        }
    }

    /**
     * Receives an order from the ServiceLayer and sends it to the Data Layer.
     *
     * @param order
     */
    @Override
    public void update(final Order order) {
        // Make the new command
        final Command command = buildCommand(order);

        // Create Runnables for timing out orders
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

        // Create necessary components for executing
        final ExecutorService exe = Executors.newSingleThreadExecutor();
        final Future fut = (command.getCoffeeMachineID() < 0 || command.getControllerID() < 0) ? exe.submit(upComm) : exe.submit(recComm);

        try {
            // Set the order to time out in 10 minutes
            fut.get(10, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException | TimeoutException ie) {
            // If it times out, send back an Order Timeout Response
            update(new ControllerResponse(order.getOrderID(), 2, 5, "Order Timeout"));
            orderIDtoCoffeeMachineID.remove(order.getOrderID());
        }
    }

    /**
     * This method takes an order and builds a command. In doing do, it must determine which coffee machine to give the
     * order to, and if the order is simple, advanced, or programmable.
     *
     * @param order
     * @return
     */
    public Command buildCommand(Order order) {
        // Get all the machines at the order's address
        ArrayList<CoffeeMachine> machines = this.databaseConnection.getCoffeeMachinesAtAddress(order.getStreetAddress(), order.getZipCode());

        // Set a null machine
        CoffeeMachine primaryMach = new CoffeeMachine(-1, -1, "Simple");

        // Determine if the order is simple
        boolean orderIsSimple = order.getOptions() == null || order.getOptions().size() == 0;

        // Get the ingredients for an order based on the drink's ingredients table
        ArrayList<String> ingredientsForDrink = this.databaseConnection.getIngredients(order.getDrinkName());
        ArrayList<Option> ingredients = new ArrayList<>();
        if(order.getOptions() != null) {
            for (Option option : order.getOptions()) {
                if (ingredientsForDrink.contains(option.getName())) {
                    ingredients.add(option);
                    option.setIsIngredient(true);
                }
            }
        }

        // If the order is not simple and has no ingredients, it is advanced/automated
        boolean orderIsAutomated = !orderIsSimple && ingredients.size() == 0;

        // Go through all the coffee machines at the address
        ArrayList<CoffeeMachine> machinesToRemove = new ArrayList<>();
        for (CoffeeMachine c : machines) {
            // remove coffee machines that can't make the drink, or aren't advanced enough to make the drink
            if (!this.databaseConnection.getDrinksForCoffeeMachine(c.getMachineId()).contains(order.getDrinkName()) ||
                    (!orderIsSimple && c.getTypeOfMachine().equals("Simple")) ||
                    (!orderIsSimple && !orderIsAutomated && c.getTypeOfMachine().equals("Automated"))) {
                machinesToRemove.add(c);
            }
        }

        // Remove machines that can't make this order
        machines.removeAll(machinesToRemove);

        // If we have machines left after that, choose the lowest skilled machine available
        if (machines.size() > 0) {
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

        // Remember which machine we send this order to
        this.orderIDtoCoffeeMachineID.put(order.getOrderID(), primaryMach.getMachineId());

        // Make a new command based on the machine's level
        Command command;
        if (primaryMach.getTypeOfMachine().equals("Simple")) {
            command = simpleCoffeeMaker.buildCommand(order, primaryMach);
        } else if (primaryMach.getTypeOfMachine().equals("Automated")) {
            command = advancedCoffeeMaker.buildCommand(order, primaryMach);
        } else {
            command = programmableCoffeeMaker.buildCommand(order, primaryMach);
        }

        // Set the request type based on the order
        if(orderIsSimple) {
            command.setRequestType("Simple");
        } else if(orderIsAutomated) {
            command.setRequestType("Automated");
        } else {
            command.setRequestType("Programmable");
        }

        return command;
    }

    /**
     * Builds an AppResponse from the controller's response.
     *
     * @param controllerResponse
     * @return
     */
    public AppResponse buildAppResponse(ControllerResponse controllerResponse) {
        int orderID = controllerResponse.getOrderID();

        // Check if we received this order
        if (orderIDtoCoffeeMachineID.containsKey(orderID)) {
            int status = controllerResponse.getStatus();
            int coffeeMachineID = orderIDtoCoffeeMachineID.get(orderID);
            String errorDescription = controllerResponse.getErrorDescription();
            String statusMessage = statusToMessage.getOrDefault(status, "Unknown status.");
            orderIDtoCoffeeMachineID.remove(orderID);

            return new AppResponse(orderID, coffeeMachineID, status, statusMessage, errorDescription);
        } else {
            // Return null if we haven't received this order. This happens if the order timed out.
            return null;
        }
    }
}
