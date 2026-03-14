package ua.university.order.processor;

import ua.university.order.domain.Order;
import ua.university.order.domain.OrderItem;
import ua.university.order.domain.OrderStatus;
import ua.university.order.exception.OrderProcessingException;
import ua.university.order.payment.PaymentMethod;

import java.util.HashSet;
import java.util.Set;

public class DefaultOrderProcessor extends OrderProcessorTemplate {

    public DefaultOrderProcessor(PaymentMethod paymentMethod) {
        super(paymentMethod);
    }

    @Override
    protected void validate(Order order) {

        if (order.getItems().length < 2) {
            throw new OrderProcessingException("Order must contain at least 2 items");
        }

        if (order.getStatus() != OrderStatus.NEW) {
            throw new OrderProcessingException("Order already processed");
        }
    }

    @Override
    protected void validateCategoryMix(Order order) {

        Set<String> categories = new HashSet<>();

        for (OrderItem item : order.getItems()) {
            categories.add(item.getCategory());
        }

        if (categories.size() >= 3) {
            logger.info("Discount will be applied for 3+ categories");
        }
    }
}
