package ua.university.order.processor;

import ua.university.order.domain.Money;
import ua.university.order.domain.Order;
import ua.university.order.domain.OrderItem;
import ua.university.order.domain.OrderStatus;
import ua.university.order.payment.PaymentMethod;

import java.util.logging.Logger;

public abstract class OrderProcessorTemplate {

    public final void process(Order order, PaymentMethod paymentMethod) {

        validate(order);
        validateCategoryMix(order);

        Money total = calculate(order);

        paymentMethod.pay(total);

        finish(order);
    }

    protected abstract void validate(Order order);

    protected abstract void validateCategoryMix(Order order);

    protected Money calculate(Order order) {

        Money sum = new Money(0);

        for (OrderItem item : order.getItems()) {
            sum = sum.add(item.total());
        }

        return sum;
    }

    protected void finish(Order order) {
        order.setStatus(OrderStatus.PAID);
    }

}
