package ServiceLayer;

import BusinessLayer.AppResponse;
import BusinessLayer.Option;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ApplicationInterface extends Publisher {
    // Order Keys
    private static final String O_ORDER_KEY = "order";
    private static final String O_ORDER_ID_KEY = "orderID";
    private static final String O_ADDRESS_KEY = "address";
    private static final String O_STREET_KEY = "street";
    private static final String O_ZIP_KEY = "ZIP";
    private static final String O_DRINK_NAME_KEY = "drink";
    private static final String O_CONDIMENTS_KEY = "condiments";
    private static final String O_CONDIMENT_NAME_KEY = "name";
    private static final String O_CONDIMENT_QTY_KEY = "qty";

    // App Response Keys
    private static final String AR_USER_RESPONSE_KEY = "user-response";
    private static final String AR_ORDER_ID_KEY = "orderID";
    private static final String AR_COFFEE_MACHINE_ID_KEY = "coffee_machine_id";
    private static final String AR_STATUS_KEY = "status";
    private static final String AR_STATUS_MESSAGE_KEY = "status-message";
    private static final String AR_ERROR_MESSAGE_KEY = "error-message";


    HashMap<Integer, Order> orders;

    public ApplicationInterface() {
        orders = new HashMap<>();
        this.eventChannel = new EventChannel();
    }

    public void placeOrder(String jsonOrder) {
        try {
            Order newOrder = parseOrder(jsonOrder);
            orders.put(newOrder.getOrderID(), newOrder);

            publishEvent(newOrder);
        } catch (JSONException exception) {
            System.out.println("Oops! Something was formatted incorrectly in the order:\"\n" + jsonOrder + "\n\". " +
                    "It caused the following error: \"" + exception.getMessage() + "\" Please try again!");
        }
    }

    private Order parseOrder(String jsonOrderAsString) throws JSONException {
        JSONObject jsonOrder = new JSONObject(jsonOrderAsString).getJSONObject(O_ORDER_KEY);

        int orderID = jsonOrder.getInt(O_ORDER_ID_KEY);

        JSONObject jsonAddress = jsonOrder.getJSONObject(O_ADDRESS_KEY);
        String streetAddress = jsonAddress.getString(O_STREET_KEY);
        int zipCode = jsonAddress.getInt(O_ZIP_KEY);

        String drinkName = jsonOrder.getString(O_DRINK_NAME_KEY);

        JSONArray condiments;
        ArrayList<Option> orderOptions = null;
        try {
            condiments = jsonOrder.getJSONArray(O_CONDIMENTS_KEY);
            orderOptions = new ArrayList<>();
            for(int i = 0; i < condiments.length(); i ++) {
                JSONObject jsonOption = condiments.getJSONObject(i);
                String optionName = jsonOption.getString(O_CONDIMENT_NAME_KEY);
                int optionQuantity = jsonOption.getInt(O_CONDIMENT_QTY_KEY);

                Option option = new Option(optionName, optionQuantity);
                orderOptions.add(option);
            }
        } catch (JSONException ignored) {}



        return new Order(orderID, streetAddress, zipCode, drinkName, orderOptions);
    }

    public void returnAppResponse(AppResponse appResponse) {
        JSONObject jsonAppResponse = new JSONObject().put(AR_USER_RESPONSE_KEY, new JSONObject());
        JSONObject jsonUserResponse = jsonAppResponse.getJSONObject(AR_USER_RESPONSE_KEY);

        jsonUserResponse.put(AR_ORDER_ID_KEY, appResponse.getOrderID());
        jsonUserResponse.put(AR_COFFEE_MACHINE_ID_KEY, appResponse.getCoffeeMachineID());
        jsonUserResponse.put(AR_STATUS_KEY, appResponse.getStatus());
        jsonUserResponse.put(AR_STATUS_MESSAGE_KEY, appResponse.getStatusMessage());

        String errorMessage = appResponse.getErrorMessage();
        if(errorMessage != null && errorMessage.length() > 0) {
            jsonUserResponse.put(AR_ERROR_MESSAGE_KEY, errorMessage);
        }

        System.out.println("ApplicationInterface responded with: \"\n" + jsonAppResponse.toString(4) + "\n\"");
    }

    @Override
    void publishEvent(Order order) {
        eventChannel.notifySubscribers(order);
    }

    public EventChannel getEventChannel() {
        return this.eventChannel;
    }
}