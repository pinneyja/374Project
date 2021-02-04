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

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
//	
	
//	@Test
//	public void testCommandController()
//	{
//		ControllerInterface con = new ControllerInterface();
//        BusinessLayer.InterLayerCommunication.Option op = new BusinessLayer.InterLayerCommunication.Option("sugar", 7);
//        ArrayList<BusinessLayer.InterLayerCommunication.Option> ops = new ArrayList<BusinessLayer.InterLayerCommunication.Option>();
//        ops.add(op);
//        Command com = new Command(6, 7, 8, "Frappe", "Automated", ops);
//        String strCom = con.sendCommand(com);  
//        
//		assertEquals("{\"command\": {\n"+
//    "\"Options\": [\n"+
//        "{\n"+
//            "\"qty\": 1,\n"+
//            "\"Name\": \"Sugar\"\n"+
//        "},\n"+
//        "{\n" +
//            "\"qty\": 2,\n" + 
//            "\"Name\": \"Cream\"\n"+
//        "}\n" +
//    "],\n" + 
//    "\"controller_id\": 2,\n" + 
//    "\"orderID\": 1,\n"+
//    "\"coffee_machine_id\": 1,\n"+
//    "\"DrinkName\": \"Americano\",\n"+
//    "\"Requesttype\": \"Automated\"\n"+
//"}}", strCom);
//	}
	
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
        
//        
//        "2":{
//			"MachineID":2,
//			"Controller":1,
        
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
