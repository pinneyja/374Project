package DataLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import BusinessLayer.AppResponse;
import BusinessLayer.CoffeeMaker;
import BusinessLayer.Option;
import org.json.JSONArray;
import org.json.JSONObject;
 

public class DatabaseConnection {
 
    public DatabaseConnection() {
    }

    // public DatabaseConnection(Database db) {
    // }

public ArrayList<CoffeeMachine> getCoffeeMachinesAtAddress(String address, int zipCode){
        //So, ideal return would be {[coffeeMachID:"1", type:""],[coffeeMachID:"2", type:""]}
  
        //JSON parser object to parse read file

        List<CoffeeMachine> controllerList = new ArrayList<CoffeeMachine>(); 

        String str;
		try {
			str = createControllerResponseString(new File("controllers.json"));
			 ArrayList<CoffeeMachine> machines= parseCM(str);

		        return machines;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }
	public String createControllerResponseString(File controllerResponseFile) throws FileNotFoundException {
		FileReader reader = new FileReader(controllerResponseFile);
		StringBuilder sb = new StringBuilder();

		try {
			while (reader.read() != -1) {
				sb.append(reader.read());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonCRAsString = sb.toString();
		return jsonCRAsString;
	}

    public ArrayList<CoffeeMachine> parseCM(String str){
        ArrayList controllerList= new ArrayList<CoffeeMachine>();

        JSONArray something = new JSONArray(str);
        for(int i=0; i<something.length(); i++){
            JSONObject j=something.getJSONObject(i);
            CoffeeMachine currentMachine = new CoffeeMachine(j.getInt("coffee_machine_id") ,j.getInt("ControllerID"), j.getString("Type"));
            controllerList.add(currentMachine);
            
        }
        return controllerList;
    }

}
