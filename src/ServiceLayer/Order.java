package ServiceLayer;

import BusinessLayer.InterLayerCommunication.Option;

import java.util.ArrayList;

public class Order {
    private int orderID, zipCode;
    private String streetAddress, drinkName;
    ArrayList<Option> options;

    public Order(int orderID, String streetAddress, int zipCode, String drinkName, ArrayList<Option> options) {
        this.orderID = orderID;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.drinkName = drinkName;
        this.options = options;
    }

    public int getOrderID() {
        return this.orderID;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public int getZipCode() {
        return this.zipCode;
    }

    public String getDrinkName() {
        return this.drinkName;
    }

    public ArrayList<Option> getOptions() {
        return this.options;
    }
}