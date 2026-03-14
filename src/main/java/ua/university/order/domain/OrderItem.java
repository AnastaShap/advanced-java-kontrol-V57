package ua.university.order.domain;

public class OrderItem {
    private final String productName;
    private final String category;
    private final int quantity;
    private final Money price;

    public OrderItem(String productName, String category, int quantity, Money price) {
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public Money total() {
        return price.multiply(quantity);
    }
    @Override
    public String toString() { return "Item{name='" + productName + "', price=" + price + "}"; }

}
