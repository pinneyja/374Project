package DataLayer;

import Helpers.Utilities;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import BusinessLayer.RecipeCreation.RecipeStep;

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
    private static final String KEY_DRINK_TYPE = "DrinkTypes";
    private static final String KEY_DRINK_RECIPE = "RecipeNeeded";
    private static final String KEY_INGREDIENT_TABLE = "Ingredient";
    private static final String KEY_COMMAND_STEP = "CommandStep";
    private static final String KEY_CONDIMENT_TABLE = "Condiment";
    private static final String KEY_RECIPE_OBJECT = "Object";

    

    
    private JSONObject jsonDatabase;

    public DatabaseConnection() {
        jsonDatabase = new JSONObject(Utilities.readStringFromLocalFile(DB_FILE_NAME)); //initializes database
    }

    //Searches database for coffee machines at a specified location
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

    //Searches coffee machines and returns an ArrayList of what a specified coffee machine can make
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
    
    //Searches the drink ingredients table and returns an ArrayList of ingredient names of the specified drink
    public ArrayList<String> getIngredients(String drinkName){
    	JSONObject drinkIngredientTable = jsonDatabase.getJSONObject(KEY_DRINK_INGREDIENT_TABLE);
    	JSONObject drink;
    	
        try {
            drink = drinkIngredientTable.getJSONObject(drinkName);
        } catch (Exception e) {
            return new ArrayList<>();
        }
        JSONArray drinkIngredientArray = drink.getJSONArray(KEY_DRINK_INGREDIENT_INGREDIENT);
        ArrayList<String> drinkTypes = new ArrayList<>();

        for (int i = 0; i < drinkIngredientArray.length(); i++) {
            drinkTypes.add(drinkIngredientArray.getString(i));
        }

        return drinkTypes;
    }
    
    //Searches drink types and returns the recipe of that drink if it exists in an ArrayList
    public ArrayList<RecipeStep> getRecipeSteps(String drinkName) {
    	JSONObject storedDrinkTable = jsonDatabase.getJSONObject(KEY_DRINK_TYPE);
    	ArrayList<RecipeStep> steps=new ArrayList<RecipeStep>();
    	JSONObject drink;
    	try {
            drink = storedDrinkTable.getJSONObject(drinkName);
        } catch (Exception e) {
            return steps;
        }
    	JSONObject recipe=drink.getJSONObject(KEY_DRINK_RECIPE);
    	
    	for (int i = 1; i < recipe.length()+1; i++) {
    		JSONObject obj=recipe.getJSONObject(Integer.toString(i));  			
    		steps.add(new RecipeStep(obj.getString(KEY_COMMAND_STEP), obj.getString(KEY_RECIPE_OBJECT)));
    	}
    	return steps;
    }
    
    //Searches condiments and ingredients and returns the command step of the ingredient 
    public String getCommandStep(String ingredientName) {
    	JSONObject ingredientTable = jsonDatabase.getJSONObject(KEY_INGREDIENT_TABLE);
    	JSONObject condTable = jsonDatabase.getJSONObject(KEY_CONDIMENT_TABLE);
    	
    	JSONObject ingredient;
    	try {
    		ingredient = ingredientTable.getJSONObject(ingredientName);
        } catch (Exception e) {
            try {
            	ingredient = condTable.getJSONObject(ingredientName);
            } catch(Exception v) {
            	return ingredientName + " is not a offered ingredient or condiment";
            }
        }
    	return ingredient.getString(KEY_COMMAND_STEP);
    }
}
