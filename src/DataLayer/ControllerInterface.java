package DataLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import BusinessLayer.AppResponse;
import BusinessLayer.Option;
import org.json.JSONArray;
import org.json.JSONObject;

import BusinessLayer.Command;


public class ControllerInterface implements Subject {

    // Controller Response Keys 
    private static final String CR_DRINK_KEY = "drinkresponse";
    private static final String CR_ORDER_ID_KEY = "orderID";
    private static final String CR_STATUS_KEY = "status";
    private static final String CR_ERROR_CODE_KEY = "errorcode";
    private static final String CR_ERROR_DESC_KEY = "errordesc";

    // Command Keys
	private static final String C_COMMAND_KEY = "command";
	private static final String C_CONTROLLER_ID_KEY = "controller_id";
	private static final String C_COFFEE_MACHINE_ID_KEY = "coffee_machine_id";
	private static final String C_ORDER_ID_KEY = "orderID";
	private static final String C_DRINK_NAME_KEY = "DrinkName";
	private static final String C_REQUEST_TYPE_KEY = "Requesttype";
	private static final String C_OPTIONS_KEY = "Options";
	private static final String C_OPTION_NAME_KEY = "Name";
	private static final String C_OPTION_QUANTITY_KEY = "qty";

	Command command;
	ControllerResponse controllerResponse;
		
	public ControllerInterface() {
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
	
	
	public String sendCommand (Command command) {
		JSONObject jsonCommand = new JSONObject().put(C_COMMAND_KEY, new JSONObject());
		JSONObject jsonCommandBody = jsonCommand.getJSONObject(C_COMMAND_KEY);

		jsonCommandBody.put(C_CONTROLLER_ID_KEY, command.getControllerID());
		jsonCommandBody.put(C_COFFEE_MACHINE_ID_KEY, command.getCoffeeMachineID());
		jsonCommandBody.put(C_ORDER_ID_KEY, command.getOrderID());
		jsonCommandBody.put(C_DRINK_NAME_KEY, command.getDrinkName());
		jsonCommandBody.put(C_REQUEST_TYPE_KEY, command.getRequestType());

		ArrayList<Option> options = command.getOptions();
		if(options != null) {
			jsonCommandBody.put(C_OPTIONS_KEY, new JSONArray());
			JSONArray jsonCommandBodyOptions = jsonCommandBody.getJSONArray(C_OPTIONS_KEY);

			for(Option option : options) {
				JSONObject jsonOption = new JSONObject();
				jsonOption.put(C_OPTION_NAME_KEY, option.getName());
				jsonOption.put(C_OPTION_QUANTITY_KEY, option.getQuantity());

				jsonCommandBodyOptions.put(jsonOption);
			}
		}

		String jsonCommandString = jsonCommand.toString(4);
		System.out.println("Sending this command via hardware to the controller:\"\n" + jsonCommandString + "\n\"");

		return jsonCommandString;
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