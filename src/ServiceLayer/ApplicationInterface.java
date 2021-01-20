package ServiceLayer;

import BusinessLayer.Option;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ApplicationInterface extends Publisher {
    HashMap<Integer, Order> orders;

    public ApplicationInterface() {
        orders = new HashMap<>();
        this.eventChannel = new EventChannel();
    }

    public void placeOrder(String jsonOrder) {
        Order newOrder = parseOrder(jsonOrder);
        orders.put(newOrder.getOrderID(), newOrder);
        eventChannel.notifySubscribers(newOrder);
    }

    private Order parseOrder(String jsonOrderAsString) {
        JSONObject jsonOrder = new JSONObject(jsonOrderAsString).getJSONObject("order");

        int orderID = jsonOrder.getInt("orderID");

        JSONObject jsonAddress = jsonOrder.getJSONObject("address");
        String streetAddress = jsonAddress.getString("street");
        int zipCode = jsonAddress.getInt("ZIP");

        String drinkName = jsonOrder.getString("drink");

        JSONArray condiments;
        ArrayList<Option> orderOptions = null;
        try {
            condiments = jsonOrder.getJSONArray("condiments");
            orderOptions = new ArrayList<>();
            for(int i = 0; i < condiments.length(); i ++) {
                JSONObject jsonOption = condiments.getJSONObject(i);
                String optionName = jsonOption.getString("name");
                int optionQuantity = jsonOption.getInt("qty");

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