package BusinessLayer;

import DataLayer.CoffeeMachine;
import DataLayer.ControllerInterface;
import DataLayer.ControllerResponse;
import DataLayer.DatabaseConnection;
import ServiceLayer.ApplicationInterface;
import ServiceLayer.Order;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
    public void update(final Order order) {
        final Command command = buildCommand(order);
        
//        final Duration timed = Duration.ofSeconds(10);
//        ExecutorService dotExe = Executors.newSingleThreadExecutor();
//        
//        final Future<String> fut = dotExe.submit(new Callable() {
//        	@Override
//        	public String call*( th)
//        }
        
        final Runnable recComm = new Thread()
        		{
        			@Override
        			public void run()
        			{
        				 controllerInterface.receiveCommand(command);
        			}
        		};
        		
        final Runnable upComm = new Thread()
        		{
        			@Override
        			public void run()
        			{
        	            update(new ControllerResponse(order.getOrderID(), 1, -1, "No machines available."));
 
        			}
        		};
        		
        final ExecutorService exe = Executors.newSingleThreadExecutor();
        final Future fut;

        if(command.getCoffeeMachineID() < 0 || command.getControllerID() < 0) {
//            update(new ControllerResponse(order.getOrderID(), 1, -1, "No machines available."));
        	
        	fut = exe.submit(upComm);
         
        	
        } else {
//            controllerInterface.receiveCommand(command);
            fut = exe.submit(recComm);

        	
        }
        
        try {
        	fut.get(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException ie)
        {
        	
        }
        catch(ExecutionException ee)
        {
        	
        }
        catch (TimeoutException  te)
        {
        	
        }
      
        
    }

    public Command buildCommand(Order order) {
        // TODO implement logic to determine which coffee maker should build the order, then use it to build the order
        
    	ArrayList<CoffeeMachine> machines = this.databaseConnection.getCoffeeMachinesAtAddress(order.getStreetAddress(), order.getZipCode());
    	
    	CoffeeMachine primaryMach = machines.get(0);
    	
    	
    	//add more logic to preempt this addition to the hash map
    	this.orderIDtoCoffeeMachineID.put(order.getOrderID(), primaryMach.getMachineId());
    	
    	//needs after logic for returning
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
