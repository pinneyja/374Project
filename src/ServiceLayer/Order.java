package ServiceLayer;

import BusinessLayer.Option;

import java.util.ArrayList;

public class Order {
    private int orderID, zipCode;
    private String streetAddress, drinkName;
    ArrayList<Option> options;

    Order(int orderID, String streetAddress, int zipCode, String drinkName, ArrayList<Option> options) {
        this.orderID = orderID;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.drinkName = drinkName;
        this.options = options;
    }
}