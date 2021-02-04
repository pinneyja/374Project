package DataLayer;

import Helpers.Utilities;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class DatabaseConnection {

    // File Names
    private static final String DB_FILE_NAME = "db.json";

    // DB Keys
    private static final String KEY_COFFEE_MAKER_TABLE = "CoffeeMaker";
    private static final String KEY_CONTROLLER_TABLE = "Controller";
    private static final String KEY_COFFEE_MAKER_DRINK_TABLE = "CoffeemakerDrink";
    private static final String KEY_COFFEE_MAKER_CONTROLLER = "Controller";
    private static final String KEY_CONTROLLER_STREET = "Street_Address";
    private static final String KEY_CONTROLLER_ZIP_CODE = "ZIP_code";
    private static final String KEY_CONTROLLER_TYPE = "Type";
    private static final String KEY_COFFEE_MAKER_ID = "MachineID";
    private static final String KEY_COFFEE_MAKER_DRINK_DRINKS = "DrinkType";
    private static final String KEY_DRINK_INGREDIENT_TABLE = "DrinkIngredient";
    private static final String KEY_DRINK_INGREDIENT_INGREDIENT = "IngredientName";
    private static final String KEY_COFFEE_MAKER_CAPABILITY_TABLE = "CoffeeMakerCapability";
    private static final String KEY_COFFEE_MAKER_CAPABILITIES = "Capability";

    
    private JSONObject jsonDatabase;

    public DatabaseConnection() {
        jsonDatabase = new JSONObject(Utilities.readStringFromLocalFile(DB_FILE_NAME));
    }

    public ArrayList<CoffeeMachine> getCoffeeMachinesAtAddress(String address, int zipCode) {
        ArrayList<CoffeeMachine> coffeeMachinesAtAddress = new ArrayList<>();

        JSONObject coffeeMakerTable = jsonDatabase.getJSONObject(KEY_COFFEE_MAKER_TABLE);
        JSONObject controllerTable = jsonDatabase.getJSONObject(KEY_CONTROLLER_TABLE);
        JSONObject coffeeMakerCapabilityTable = jsonDatabase.getJSONObject(KEY_COFFEE_MAKER_CAPABILITY_TABLE);

        Iterator<String> coffeeMakerIDs = coffeeMakerTable.keys();

        while (coffeeMakerIDs.hasNext()) {
            String stringCoffeeMakerID = coffeeMakerIDs.next();

            JSONObject jsonCoffeeMaker = coffeeMakerTable.getJSONObject(stringCoffeeMakerID);
            JSONObject jsonCoffeeMakerCapability = coffeeMakerCapabilityTable.getJSONObject(stringCoffeeMakerID);
            JSONArray jsonCoffeeMakerCapabilities = jsonCoffeeMakerCapability.getJSONArray(KEY_COFFEE_MAKER_CAPABILITIES);
            String controllerNumber = Integer.toString(jsonCoffeeMaker.getInt(KEY_COFFEE_MAKER_CONTROLLER));
            JSONObject jsonController = controllerTable.getJSONObject(controllerNumber);

            boolean jsonControllerMatchesAddress = jsonController.getString(KEY_CONTROLLER_STREET).equals(address) &&
                    jsonController.getInt(KEY_CONTROLLER_ZIP_CODE) == (zipCode);

            if (jsonControllerMatchesAddress) {
                int coffeeMakerID = jsonCoffeeMaker.getInt(KEY_COFFEE_MAKER_ID);
                int coffeeMakerControllerID = jsonCoffeeMaker.getInt(KEY_COFFEE_MAKER_CONTROLLER);
                String highestCoffeeMakerCapability = "Simple";
                int capabilityValue = 1;

                for(int i = 0; i < jsonCoffeeMakerCapabilities.length(); i ++) {
                    String capability = jsonCoffeeMakerCapabilities.getString(i);
                    if(capability.equals("Automated") && capabilityValue < 2) {
                        highestCoffeeMakerCapability = capability;
                        capabilityValue = 2;
                    } else {
                        highestCoffeeMakerCapability = capability;
                        capabilityValue = 3;
                    }
                }

                CoffeeMachine currentMachine = new CoffeeMachine(coffeeMakerID, coffeeMakerControllerID, highestCoffeeMakerCapability);
                coffeeMachinesAtAddress.add(currentMachine);
            }
        }

        return coffeeMachinesAtAddress;
    }

    public ArrayList<String> getDrinksForCoffeeMachine(int coffeeMachineID) {
        JSONObject drinkTable = jsonDatabase.getJSONObject(KEY_COFFEE_MAKER_DRINK_TABLE);
        JSONObject jsonCoffeeMakerDrinks;

        try {
            jsonCoffeeMakerDrinks = drinkTable.getJSONObject(Integer.toString(coffeeMachineID));
        } catch (Exception e) {
            return new ArrayList<>();
        }

        JSONArray coffeeMakerDrinksArray = jsonCoffeeMakerDrinks.getJSONArray(KEY_COFFEE_MAKER_DRINK_DRINKS);
        ArrayList<String> drinkTypes = new ArrayList<>();

        for (int i = 0; i < coffeeMakerDrinksArray.length(); i++) {
            drinkTypes.add(coffeeMakerDrinksArray.getString(i));
        }

        return drinkTypes;
    }
    
    public ArrayList<String> getIngredients(String drinkName){
    	JSONObject drinkIngredientTable = jsonDatabase.getJSONObject(KEY_DRINK_INGREDIENT_TABLE);
    	JSONObject drink;
    	
        try {
            drink = drinkIngredientTable.getJSONObject(drinkName);
        } catch (Exception e) {
//        	System.out.println("Requested drink does not exist in system!");
            return new ArrayList<>();
        }
        JSONArray drinkIngredientArray = drink.getJSONArray(KEY_DRINK_INGREDIENT_INGREDIENT);
        ArrayList<String> drinkTypes = new ArrayList<>();

        for (int i = 0; i < drinkIngredientArray.length(); i++) {
            drinkTypes.add(drinkIngredientArray.getString(i));
        }

        return drinkTypes;
    }
}
