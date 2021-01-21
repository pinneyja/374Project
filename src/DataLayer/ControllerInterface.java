package DataLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

import BusinessLayer.Command;


public class ControllerInterface implements Subject {

    // Controller Response Keys 
    private static final String CR_DRINK_KEY = "drinkresponse";
    private static final String CR_ORDER_ID_KEY = "orderID";
    private static final String CR_STATUS_KEY = "status";
    private static final String CR_ERROR_CODE_KEY = "errorcode";
    private static final String CR_ERROR_DESC_KEY = "errordesc";

	Command command;
	ControllerResponse controllerResponse;
		
	public ControllerInterface(Command c) {
		this.command = c;
	}
	
	
	/*
	 * Takes in a String of the Controller Response. Will parse the
	 * JSON and turn it into a ControllerResponse Object. This 
	 * object will be passed back to the correct CoffeeMaker
	 */	
	public ControllerResponse parseControllerResponse (String jsonCRAsString) {
		
		    JSONObject jsonDrinkResponse = new JSONObject(jsonCRAsString).getJSONObject(CR_DRINK_KEY);

	        int orderID = jsonDrinkResponse.getInt(CR_ORDER_ID_KEY);
	        
	        int status = jsonDrinkResponse.getInt(CR_STATUS_KEY);
	        
	        String errorDesc = jsonDrinkResponse.getString(CR_ERROR_DESC_KEY);
	        
	        int errorCode = jsonDrinkResponse.getInt(CR_ERROR_CODE_KEY);
	        
	        return new ControllerResponse(orderID, status, errorCode, errorDesc);
	}
	
   /*
    * This method will be called from (?). It is sent a File that gets converted 
    * to a String. This string is what will be used to parse the JSON.
    */
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
	
	
	public void sendCommand (Command c) {
		
	}

	@Override
	public void registerObserver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObserver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}
	
	
}