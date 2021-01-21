package DataLayer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 

public class DatabaseConnection {
 
    public DatabaseConnection() {
    }

    // public DatabaseConnection(Database db) {
    // }

    public ArrayList<CoffeeMachine> getCoffeeMachinesAtAddress(String address, int zipCode) {
        //So, ideal return would be {[coffeeMachID:"1", type:""],[coffeeMachID:"2", type:""]}
  
        //JSON parser object to parse read file

        JSONParser jsonParser = new JSONParser();
        List<CoffeeMachine> controllerList = new ArrayList<CoffeeMachine>();

        try (FileReader reader = new FileReader("controllers.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray controllers = (JSONArray) obj;
            System.out.println(controllers);
             
            //Iterate over con array
            controllers.forEach( con -> {
                        JSONObject controller = (JSONObject) con.get("controller");

                        if(controller.get("StreetAddress").equals(address)){
                            CoffeeMachine currentMachine = new CoffeeMachine(controller.get("coffee_machine_id") ,controller.get("ControllerID"), controller.get("Type"));
                            controllerList.add(currentMachine);
                        }
                    });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return controllerList;
    }

}
