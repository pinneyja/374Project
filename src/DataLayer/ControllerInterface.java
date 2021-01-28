package DataLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import BusinessLayer.*;
import Helpers.Utilities;
import ServiceLayer.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ControllerInterface implements DataSubject {

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

	HashSet<DataObserver> dataObservers;
	ArrayList<ControllerResponse> responses;
	int numCommands = 3;
//	ControllerResponse controllerResponse;

	public ControllerInterface() {
		dataObservers = new HashSet<>();
		responses = new ArrayList<ControllerResponse>();
	}
	
    public void readResponsesFromFile() {
        String fileData = Utilities.readStringFromLocalFile("controller-response.json");
        JSONArray jsonResponses = new JSONArray(fileData);
        for(int i = 0; i < jsonResponses.length(); i ++) {
            JSONObject jsonResponse = jsonResponses.getJSONObject(i);
            saveResponses(jsonResponse.toString());
        }
    }

    public void saveResponses(String jsonOrder) {
        try {
            ControllerResponse newResponse = parseControllerResponse(jsonOrder);
            responses.add(newResponse);

        } catch (JSONException exception) {
            System.out.println("Oops! Something was formatted incorrectly in the order:\"\n" + jsonOrder + "\n\". " +
                    "It caused the following error: \"" + exception.getMessage() + "\" Please try again!");
        }
    }

	/*
	 * This method will be called from (?). It is sent a File that gets converted to
	 * a String. This string is what will be used to parse the JSON.
	 * Takes in a String of the Controller Response. Will JSONParser the JSON and turn it
	 * into a ControllerResponse Object. This object will be passed back to the
	 * correct CoffeeMaker
	 */
	public ControllerResponse parseControllerResponse(String jsonCRAsString) {
		
		JSONObject jsonDrinkResponse = new JSONObject(jsonCRAsString).getJSONObject(CR_DRINK_KEY);
		int orderID = jsonDrinkResponse.getInt(CR_ORDER_ID_KEY);
		int status = jsonDrinkResponse.getInt(CR_STATUS_KEY);
		String errorDesc = null;
		Integer errorCode = null;
		
		if (jsonDrinkResponse.has(CR_ERROR_DESC_KEY)) {
			errorDesc = jsonDrinkResponse.getString(CR_ERROR_DESC_KEY);
		}
		
		if (jsonDrinkResponse.has(CR_ERROR_CODE_KEY)) {
			errorCode = jsonDrinkResponse.getInt(CR_ERROR_CODE_KEY);
		}
		
		return new ControllerResponse(orderID, status, errorCode, errorDesc);
	}

	/*
	 * This method handles sending the JSON to a external process.
	 */
	public String sendCommand(Command command) {
		JSONObject jsonCommand = new JSONObject().put(C_COMMAND_KEY, new JSONObject());
		JSONObject jsonCommandBody = jsonCommand.getJSONObject(C_COMMAND_KEY);

		jsonCommandBody.put(C_CONTROLLER_ID_KEY, command.getControllerID());
		jsonCommandBody.put(C_COFFEE_MACHINE_ID_KEY, command.getCoffeeMachineID());
		jsonCommandBody.put(C_ORDER_ID_KEY, command.getOrderID());
		jsonCommandBody.put(C_DRINK_NAME_KEY, command.getDrinkName());
		jsonCommandBody.put(C_REQUEST_TYPE_KEY, command.getRequestType());

		ArrayList<Option> options = command.getOptions();
		if (options != null) {
			jsonCommandBody.put(C_OPTIONS_KEY, new JSONArray());
			JSONArray jsonCommandBodyOptions = jsonCommandBody.getJSONArray(C_OPTIONS_KEY);

			for (Option option : options) {
				JSONObject jsonOption = new JSONObject();
				jsonOption.put(C_OPTION_NAME_KEY, option.getName());
				jsonOption.put(C_OPTION_QUANTITY_KEY, option.getQuantity());

				jsonCommandBodyOptions.put(jsonOption);
			}
		}
		String jsonCommandString = jsonCommand.toString(4) + "\n";
		String currentFile = Utilities.readStringFromLocalFile("out/Command_stream.json");
		currentFile += jsonCommandString;
		Utilities.writeStringToLocalFile("Command_stream.json", currentFile);
		System.out.println("Sending this command via hardware to the controller:\"\n" + jsonCommandString + "\n\"");
		numCommands --;
		if(numCommands == 0) {
			sendBackResponse();
		}
		return jsonCommandString;
	}

	/*
	 * Currently rigged to finish the process as if the external process sent back a
	 * response.
	 */
	public void sendBackResponse(){
		// Send command
		readResponsesFromFile();
		for (int i = 0; i < this.responses.size(); i++) {
			notifyObservers(this.responses.get(i));
		}
	}

	/*
	 * The following methods handle interactions between the CoffeeMaker and the
	 * ControllerInterface
	 */
	@Override
	public void registerObserver(DataObserver dataObserver) {
		dataObservers.add(dataObserver);
	}

	@Override
	public void removeObserver(DataObserver dataObserver) {
		dataObservers.remove(dataObserver);
	}

	@Override
	public void notifyObservers(ControllerResponse response) {
		for(DataObserver dataObserver : dataObservers) {
			dataObserver.update(response);
		}
	}
}
