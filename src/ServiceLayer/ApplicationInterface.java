package ServiceLayer;

import BusinessLayer.Option;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ApplicationInterface extends Publisher {
    private static final String ORDER_KEY = "order";
    private static final String ORDER_ID_KEY = "orderID";
    private static final String ADDRESS_KEY = "address";
    private static final String STREET_KEY = "street";
    private static final String ZIP_KEY = "ZIP";
    private static final String DRINK_NAME_KEY = "drink";
    private static final String CONDIMENTS_KEY = "condiments";
    private static final String CONDIMENT_NAME_KEY = "name";
    private static final String CONDIMENT_QTY_KEY = "qty";

    HashMap<Integer, Order> orders;

    public ApplicationInterface() {
        orders = new HashMap<>();
        this.eventChannel = new EventChannel();
    }

    public void placeOrder(String jsonOrder) {
        try {
            Order newOrder = parseOrder(jsonOrder);
            orders.put(newOrder.getOrderID(), newOrder);
            eventChannel.notifySubscribers(newOrder);
        } catch (JSONException exception) {
            System.out.println("Oops! Something was formatted incorrectly in the order:\"\n" + jsonOrder + "\n\". " +
                    "It caused the following error: \"" + exception.getMessage() + "\" Please try again!");
        }
    }

    private Order parseOrder(String jsonOrderAsString) throws JSONException {
        JSONObject jsonOrder = new JSONObject(jsonOrderAsString).getJSONObject(ORDER_KEY);

        int orderID = jsonOrder.getInt(ORDER_ID_KEY);

        JSONObject jsonAddress = jsonOrder.getJSONObject(ADDRESS_KEY);
        String streetAddress = jsonAddress.getString(STREET_KEY);
        int zipCode = jsonAddress.getInt(ZIP_KEY);

        String drinkName = jsonOrder.getString(DRINK_NAME_KEY);

        JSONArray condiments;
        ArrayList<Option> orderOptions = null;
        try {
            condiments = jsonOrder.getJSONArray(CONDIMENTS_KEY);
            orderOptions = new ArrayList<>();
            for(int i = 0; i < condiments.length(); i ++) {
                JSONObject jsonOption = condiments.getJSONObject(i);
                String optionName = jsonOption.getString(CONDIMENT_NAME_KEY);
                int optionQuantity = jsonOption.getInt(CONDIMENT_QTY_KEY);

                Option option = new Option(optionName, optionQuantity);
                orderOptions.add(option);
            }
        } catch (JSONException ignored) {}



        return new Order(orderID, streetAddress, zipCode, drinkName, orderOptions);
    }

    @Override
    void publishEvent() {

    }

    public EventChannel getEventChannel() {
        return this.eventChannel;
    }
}