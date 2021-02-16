import java.util.ArrayList;

import BusinessLayer.InterLayerCommunication.Command;
import BusinessLayer.InterLayerCommunication.Option;
import DataLayer.ControllerInterface;
import Helpers.Utilities;
import ServiceLayer.ApplicationInterface;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting interface");

        Utilities.writeStringToLocalFile("App-response.json", ""); // this clears out the App-response.json file for a new run
        Utilities.writeStringToLocalFile("Command_stream.json", "");
        ApplicationInterface applicationInterface = new ApplicationInterface();
        applicationInterface.readOrdersFromFile("order-input.json");

//        testControllerInterface();
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