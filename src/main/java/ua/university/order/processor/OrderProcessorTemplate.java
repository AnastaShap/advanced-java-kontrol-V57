package ua.university.order.processor;

import ua.university.order.domain.Money;
import ua.university.order.domain.Order;
import ua.university.order.domain.OrderItem;
import ua.university.order.domain.OrderStatus;
import ua.university.order.exception.AppException;
import ua.university.order.exception.OrderProcessingException;
import ua.university.order.payment.PaymentMethod;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class OrderProcessorTemplate {

    protected static final Logger logger = Logger.getLogger(OrderProcessorTemplate.class.getName());

    protected final PaymentMethod paymentMethod;

    protected OrderProcessorTemplate(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public final void process(Order order) {

        try{
            validate(order);
            validateCategoryMix(order);
            Money total = calculate(order);
            paymentMethod.pay(total);
            finish(order);

        } catch (AppException e) {
        logger.warning("Business error: " + e.getMessage());
        throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Infrastructure failure", e);
            throw new OrderProcessingException("Critical failure", e); // Chaining
        }
    }

    protected abstract void validate(Order order);

    protected abstract void validateCategoryMix(Order order);

    protected Money calculate(Order order) {

        Money sum = new Money(0);

        Set<String> categories = new HashSet<>();

        for (OrderItem item : order.getItems()) {
            sum = sum.add(item.total());
            categories.add(item.getCategory());
        }

        if (categories.size() >= 3) {
            sum = sum.multiply(0.95);
        }

        return sum;
    }

    protected void finish(Order order) {
        order.setStatus(OrderStatus.PAID);
    }

    protected void notifyCustomer(Order order) {
        logger.info("Customer notified for order " + order.getId());
    }

}
