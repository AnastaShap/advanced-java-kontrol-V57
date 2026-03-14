package ua.university.order.domain;

public class Order {
    private final String id;
    private final OrderItem[] items;
    private OrderStatus status;

    public Order(String id, OrderItem[] items) {
        this.id = id;
        this.items = items.clone(); // defensive copy
        this.status = OrderStatus.NEW;
    }

    /*public OrderItem[] getItems() {
        return items.clone(); // Повертаємо копію
    }*/

    @Override
    public String toString() {
        return super.toString();
    }


}
