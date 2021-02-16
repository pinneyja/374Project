package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import BusinessLayer.InterLayerCommunication.Option;
import BusinessLayer.OrderManager;
import Helpers.Utilities;
import ServiceLayer.ApplicationInterface;
import ServiceLayer.Order;

class DunkinTest {
	
	@Test
	public void u1Test()
	{
		Utilities.writeStringToLocalFile("App-response.json", ""); // this clears out the App-response.json file for a new run
        Utilities.writeStringToLocalFile("Command_stream.json", "");
        ApplicationInterface applicationInterface = new ApplicationInterface();
        applicationInterface.readOrdersFromFile("order-input.json");
        
        ArrayList<Option> ops = new ArrayList<Option>();
        Order o1 = new Order(0, "200 N. Main",47803,"Special", ops);
        OrderManager om = new OrderManager(applicationInterface);
        
        assertEquals(2, om.buildCommand(o1).getCoffeeMachineID());
	}
	
	@Test
	public void u2Test()
	{
		Utilities.writeStringToLocalFile("App-response.json", ""); // this clears out the App-response.json file for a new run
        Utilities.writeStringToLocalFile("Command_stream.json", "");
        ApplicationInterface applicationInterface = new ApplicationInterface();
        applicationInterface.readOrdersFromFile("order-input.json");
        
        ArrayList<Option> ops = new ArrayList<Option>();
        ops.add(new Option("Cream",1));
        Order o1 = new Order(0, "3 S. Walnut",60601,"Latte", ops);
        OrderManager om = new OrderManager(applicationInterface);
        System.out.println(om.buildCommand(o1).getCoffeeMachineID());
        assertEquals(1, om.buildCommand(o1).getCoffeeMachineID());
        
	}

    @Test
    public void u3Test()
    {
        Utilities.writeStringToLocalFile("App-response.json", ""); // this clears out the App-response.json file for a new run
        Utilities.writeStringToLocalFile("Command_stream.json", "");
        ApplicationInterface applicationInterface = new ApplicationInterface();
        applicationInterface.readOrdersFromFile("order-input.json");

        ArrayList<Option> ops = new ArrayList<>();
        ops.add(new Option("Sugar",1));
        ops.add(new Option("Cream",2));
        ops.add(new Option("Coffee",1));
        Order o1 = new Order(0, "200 N. Main",47803,"Latte", ops);
        OrderManager om = new OrderManager(applicationInterface);
        System.out.println(om.buildCommand(o1).getCoffeeMachineID());
        assertEquals(3, om.buildCommand(o1).getCoffeeMachineID());
    }
}
