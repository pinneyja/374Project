import java.util.ArrayList;

import BusinessLayer.InterLayerCommunication.Command;
import BusinessLayer.InterLayerCommunication.Option;
import DataLayer.ControllerInterface;
import Helpers.Utilities;
import ServiceLayer.ApplicationInterface;

public class Main {
    private static final String[] ORDERS = {
            "{\n" +
                    "  \"order\": { \"orderID\": 1,\n" +
                    "            \"address\": {\n" +
                    "                  \"street\": \"200 N Main\",\n" +
                    "                  \"ZIP\": 47803\n" +
                    "            },\n" +
                    "            \"drink\": \"Americano\",\n" +
                    "            \"condiments\": [\n" +
                    "                {\"name\": \"Sugar\", \"qty\": 1},\n" +
                    "                {\"name\": \"Cream\", \"qty\": 2}\n" +
                    "            ]\n" +
                    "            }\n" +
                    "}",

            "{\n" +
                    "  \"order\": { \"orderID\": 2,\n" +
                    "            \"address\": {\n" +
                    "                  \"street\": \"200 N Main\",\n" +
                    "                  \"ZIP\": 47803\n" +
                    "            },\n" +
                    "            \"drink\": \"Expresso\"\n" +
                    "            }\n" +
                    "}",

            "{\n" +
                    "  \"order\": { \"orderID\": 3,\n" +
                    "            \"address\": {\n" +
                    "                  \"street\": \"200 N Main\",\n" +
                    "                  \"ZIP\": 47803\n" +
                    "            },\n" +
                    "            \"drink\": \"Pumpkin Spice\",\n" +
                    "            \"condiments\": [\n" +
                    "                {\"name\": \"Cream\", \"qty\": 1}\n" +
                    "            ]\n" +
                    "            }\n" +
                    "}"
    };

    public static void main(String[] args) {
        System.out.println("Starting interface");
        
//        testControllerInterface();
//
        Utilities.writeStringToLocalFile("App-response.json", ""); // this clears out the App-response.json file for a new run
        Utilities.writeStringToLocalFile("Command_stream.json", "");
        ApplicationInterface applicationInterface = new ApplicationInterface();
        applicationInterface.readOrdersFromFile("order-input.json"); // TODO: uncomment this line to run with file input

//        DatabaseConnection dbc=new DatabaseConnection();
//        ArrayList<CoffeeMachine> coffeee= dbc.getCoffeeMachinesAtAddress("200 N. Main", 47803);
//        System.out.println(coffeee.size());
        testControllerInterface();
    }
    
    /*
     * This method is meant to test the ControllerInterface. 
     * - Makes sure that a File is created that should be sent to some external hardware
     * - Makes sure that a File can be read from and create a Controller Response object
     */
    public static void testControllerInterface() {
        ControllerInterface con = new ControllerInterface();
        Option op = new Option("sugar", 7);
        ArrayList<Option> ops = new ArrayList<Option>();
        ops.add(op);
        Command com = new Command(6, 7, 8, "Frappe", "Automated", ops);
        con.sendCommand(com);   
        System.out.println("Finished");
    }
}