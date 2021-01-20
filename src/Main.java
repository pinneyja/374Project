import ServiceLayer.ApplicationInterface;

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
    }
}