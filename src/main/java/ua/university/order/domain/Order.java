package ua.university.order.domain;

import java.util.Objects;

// Main Entity: Order (use defensive copy)
public class Order {
    private final String id;
    private final OrderItem[] items; //array of items
    private OrderStatus status;

    // Ланцюжок конструкторів (this)
    public Order(String id) {
        this(id, new OrderItem[0]);
    }

    public Order(String id, OrderItem[] items) {
        this.id = id;
        this.items = items.clone(); // defensive copy
        this.status = OrderStatus.NEW;
    }

    public String getId() {
        return id;
    }
    public OrderItem[] getItems() {
        return items.clone();

    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {this.status = status;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
