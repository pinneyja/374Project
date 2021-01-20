import BusinessLayer.AppResponse;
import ServiceLayer.ApplicationInterface;

import java.util.ArrayList;

public class Main {
    private static final String[] ORDERS = {
            "{\n" +
                    "  \"order\": { \"orderID\": 1,\n" +
                    "            \"address\": {\n" +
                    "                  \"street\": \"200 N Main\",\n" +
                    "                  \"ZIP\": 47803\n" +
                    "            },\n" +
                    "            \"drink\": \"Americano\",\n" +
                    "            \"condiments\": [\n" +
                    "                {\"name\": \"Sugar\", \"qty\": 1},\n" +
                    "                {\"name\": \"Cream\", \"qty\": 2}\n" +
                    "            ]\n" +
                    "            }\n" +
                    "}",

            "{\n" +
                    "  \"order\": { \"orderID\": 2,\n" +
                    "            \"address\": {\n" +
                    "                  \"street\": \"200 N Main\",\n" +
                    "                  \"ZIP\": 47803\n" +
                    "            },\n" +
                    "            \"drink\": \"Expresso\"\n" +
                    "            }\n" +
                    "}",

            "{\n" +
                    "  \"order\": { \"orderID\": 3,\n" +
                    "            \"address\": {\n" +
                    "                  \"street\": \"200 N Main\",\n" +
                    "                  \"ZIP\": 47803\n" +
                    "            },\n" +
                    "            \"drink\": \"Pumpkin Spice\",\n" +
                    "            \"condiments\": [\n" +
                    "                {\"name\": \"Cream\", \"qty\": 1}\n" +
                    "            ]\n" +
                    "            }\n" +
                    "}"
    };

    public static void main(String[] args) {
        System.out.println("Starting interface");
        ApplicationInterface applicationInterface = new ApplicationInterface();

        for (String order : ORDERS) {
            applicationInterface.placeOrder(order);
        }

        // TODO: delete when done testing AppResponse
//        ArrayList<AppResponse> appResponses = new ArrayList<>();
//        appResponses.add(new AppResponse(1, 1, 0, "Your coffee has been prepared with your desired options.", null));
//        appResponses.add(new AppResponse(2, 2, 1, "Your coffee order has been cancelled.", "Out of milk, drink cancelled."));
//        appResponses.add(new AppResponse(3, 1, 1, "Your coffee order has been cancelled.", "Machine jammed."));
//
//        for(AppResponse appResponse : appResponses) {
//            applicationInterface.returnAppResponse(appResponse);
//        }
    }
}