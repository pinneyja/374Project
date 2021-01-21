package DataLayer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 

public class DatabaseConnection {
 
    public DatabaseConnection() {
    }

    // public DatabaseConnection(Database db) {
    // }

    public ArrayList<ArrayList<String>> sendMessage(String address) {
        //So, ideal return would be {[coffeeMachID:"1", type:""],[coffeeMachID:"2", type:""]}
  
        //JSON parser object to parse read file

        JSONParser jsonParser = new JSONParser();
        List<List<String>> controllerList = new ArrayList<ArrayList<String>>(); 

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
                            ArrayList currentController = new ArrayList<String>();
                            currentController.add(controller.get("ControllerID").toString());
                            currentController.add(controller.get("Type").toString());
                            controllerList.add(currentController);
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
