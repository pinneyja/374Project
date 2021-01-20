package BusinessLayer;

public class Option {
    private String name;
    private int quantity;

    Option(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
