package BusinessLayer;

public class Option {
    private String name;
    private int quantity;
    private boolean isIngredient;

    public Option(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.isIngredient = false;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isIngredient() {
        return isIngredient;
    }

    public void setIsIngredient(boolean isIngredient) {
        this.isIngredient = isIngredient;
    }
}
