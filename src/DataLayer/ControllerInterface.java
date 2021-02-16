package DataLayer;

import java.util.ArrayList;
import java.util.HashSet;

import BusinessLayer.InterLayerCommunication.Command;
import BusinessLayer.InterLayerCommunication.DataObserver;
import BusinessLayer.InterLayerCommunication.Option;
import BusinessLayer.RecipeCreation.RecipeStep;
import Helpers.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/*
 * Communicates with OrderManager. Receives Command Object from OrderManager.
 * Sends ControllerResponse Object to the Order Manager.
 */
public class ControllerInterface implements DataSubject {

    // File Names
    private static final String CONTROLLER_RESPONSE_FILE = "controller-response.json";
    private static final String COMMAND_STREAM_FILE = "Command_stream.json";
    private static final String COMMAND_STREAM_FILE_IN_OUT = "out/" + COMMAND_STREAM_FILE;

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
    
    private static final String C_RECIPE_KEY = "Recipe";
    private static final String C_RECIPE_COMMANDSTEP_KEY = "commandstep";
    private static final String C_RECIPE_OBJECT_KEY = 	"object";

    HashSet<DataObserver> dataObservers;
    int numCommands = 4; // This changes so we know when to start processing controller response file.

    /*
     * Creates a new ControllerInterface object.
     */
    public ControllerInterface() {
        dataObservers = new HashSet<>();
    }
    
    /*
     * Returns an ArrayList of ControllerResponse Objects. 
	 * Does this by using the Utilities Class to read from a file and turn the file into a String. 
	 * Once it does this, it parses the JSON to turn it into a Controller Response
     */
    private ArrayList<ControllerResponse> readResponsesFromFile() {
        ArrayList<ControllerResponse> responses = new ArrayList<>();

        String fileData = Utilities.readStringFromLocalFile(CONTROLLER_RESPONSE_FILE);
        JSONArray jsonResponses = new JSONArray(fileData);
        for (int i = 0; i < jsonResponses.length(); i++) {
            JSONObject jsonResponse = jsonResponses.getJSONObject(i);
            String stringResponse = jsonResponse.toString();
            try {
                ControllerResponse newResponse = parseControllerResponse(stringResponse);
                responses.add(newResponse);
            } catch (JSONException exception) {
                System.out.println("Oops! Something was formatted incorrectly in the order:\"\n" + stringResponse + "\n\". " +
                        "It caused the following error: \"" + exception.getMessage() + "\" Please try again!");
            }
        }

        return responses;
    }

    /*
     * Returns a Controller Response Object. Does this by parsing the String that was passed in.
     * 
     * @param String 
     */
    private ControllerResponse parseControllerResponse(String jsonCRAsString) {

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
     * Method is void. Takes in a Command Object and parses it into JSON. 
     * Then writes the JSON to a file using the Utilities Class.
     * 
     * @param Command
     */
    public void sendCommand(Command command) {
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
        
        
        ArrayList<RecipeStep> recipes = command.getRecipe();
        if (recipes != null) {
        	jsonCommandBody.put(C_RECIPE_KEY, new JSONArray());
        	JSONArray jsonCommandBodyRecipes = jsonCommandBody.getJSONArray(C_RECIPE_KEY);
        	
        	for (RecipeStep recipe : recipes) {
        		JSONObject jsonRecipe = new JSONObject();
        		jsonRecipe.put(C_RECIPE_COMMANDSTEP_KEY, recipe.getCommandStep());
        		if (recipe.getObject() != null) {
        			jsonRecipe.put(C_RECIPE_OBJECT_KEY, recipe.getObject());
        		}
        		        		
        		jsonCommandBodyRecipes.put(jsonRecipe);
        	}
        }
       

        String jsonCommandString = jsonCommand.toString(4) + "\n";
        String currentFile = Utilities.readStringFromLocalFile(COMMAND_STREAM_FILE_IN_OUT);
        currentFile += jsonCommandString;
        Utilities.writeStringToLocalFile(COMMAND_STREAM_FILE, currentFile);

        numCommands--;
        if (numCommands == 0) {
            sendBackResponses();
        }
    }

    /*
     * Method is void. Calls the readResponsesFromFile method and then 
     * iterates through the ArrayList that was created, 
     * notifying the observers of each response.
     * 
     */
    private void sendBackResponses() {
        for (ControllerResponse response : readResponsesFromFile()) {
            notifyObservers(response);
        }
    }
    

    /*
     * Part of Observer Pattern. Adds DataObserver to dataObservers instance variable.
     * 
     * @param DataObserver
     */
    @Override
    public void registerObserver(DataObserver dataObserver) {
        dataObservers.add(dataObserver);
    }

    /*
     * Part of Observer Pattern. Removes DataObserver from DataObservers instance variable.
     * 
     * @param DataObserver
     */
    @Override
    public void removeObserver(DataObserver dataObserver) {
        dataObservers.remove(dataObserver);
    }

    /*
     * Part of Observer Pattern. Notifies each of the dataObservers of the update by sending a Controller Response.
     * 
     * @param ControllerResponse
     */
    @Override
    public void notifyObservers(ControllerResponse response) {
        for (DataObserver dataObserver : dataObservers) {
            dataObserver.update(response);
        }
    }
}
