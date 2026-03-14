package ua.university.order.processor;

import ua.university.order.domain.Money;
import ua.university.order.domain.Order;
import ua.university.order.domain.OrderItem;
import ua.university.order.domain.OrderStatus;
import ua.university.order.exception.AppException;
import ua.university.order.exception.ValidationException;
import ua.university.order.payment.PaymentMethod;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class OrderProcessorTemplate {

    protected static final Logger logger = Logger.getLogger(OrderProcessorTemplate.class.getName());

    // Шаблонний метод (final)
    public final void process(Order order) throws AppException {

    }

    public final void process(Order order, PaymentMethod paymentMethod) {

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
            throw new ValidationException("Critical failure", e); // Chaining
        }
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
