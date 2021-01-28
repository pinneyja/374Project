package DataLayer;

import java.util.ArrayList;
import java.util.HashSet;

import BusinessLayer.*;
import Helpers.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    HashSet<DataObserver> dataObservers;
    int numCommands = 2; // TODO: This changes so we know when to start processing controller response file.

    public ControllerInterface() {
        dataObservers = new HashSet<>();
    }

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

        String jsonCommandString = jsonCommand.toString(4) + "\n";
        String currentFile = Utilities.readStringFromLocalFile(COMMAND_STREAM_FILE_IN_OUT);
        currentFile += jsonCommandString;
        Utilities.writeStringToLocalFile(COMMAND_STREAM_FILE, currentFile);

        numCommands--;
        if (numCommands == 0) {
            sendBackResponses();
        }
    }

    private void sendBackResponses() {
        readResponsesFromFile();

        for (ControllerResponse response : readResponsesFromFile()) {
            notifyObservers(response);
        }
    }
    
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
        for (DataObserver dataObserver : dataObservers) {
            dataObserver.update(response);
        }
    }
}
