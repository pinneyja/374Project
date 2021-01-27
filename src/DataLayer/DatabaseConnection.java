package DataLayer;

import Helpers.Utilities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import BusinessLayer.AppResponse;
import BusinessLayer.CoffeeMaker;
import BusinessLayer.Option;
import org.json.JSONArray;
import org.json.JSONObject; 

public class DatabaseConnection {
 
    public DatabaseConnection() {
    }

public ArrayList<CoffeeMachine> getCoffeeMachinesAtAddress(String address, int zipCode){
			String str = Utilities.readStringFromLocalFile("db.json");
			
			ArrayList<CoffeeMachine> machines= parseCoffeeMachinesAtAddress(str, address, zipCode);
	        return machines;
    }

    public ArrayList<CoffeeMachine> parseCoffeeMachinesAtAddress(String str, String address, int zipCode){
        ArrayList<CoffeeMachine> controllerList= new ArrayList<CoffeeMachine>();
        
        JSONObject db = new JSONObject(str.trim()); //whole db jsonObject version of db string {"Table":{}, "Table2":{}, ...}
        JSONObject coffeeMakerTable = db.getJSONObject("CoffeeMaker"); 
        JSONObject controllerTable = db.getJSONObject("Controller"); 

        Iterator<String> keys = coffeeMakerTable.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            if (coffeeMakerTable.get(key) instanceof JSONObject) {
            		//gets current machine
        			JSONObject instanceOfCoffeeMaker = (JSONObject)coffeeMakerTable.get(key);//"Name":{...} aka what we need or instance of db row
        			//Gets current machine's controller
        			String controllerNumber=String.valueOf(instanceOfCoffeeMaker.getInt("Controller")); 
        			JSONObject instanceOfController=controllerTable.getJSONObject(controllerNumber);
        
        			
        			if(instanceOfController.getString("Street_Address").equals(address) && instanceOfController.getInt("ZIP_code")==(zipCode)) {
				        CoffeeMachine currentMachine = new CoffeeMachine(instanceOfCoffeeMaker.getInt("MachineID"),
				        												 instanceOfCoffeeMaker.getInt("Controller"), 
				        												 instanceOfController.getString("Type"));
	        			controllerList.add(currentMachine);
        			}
            }
        }
        
        return controllerList;
    }
}
