package ua.university.order.payment;

import ua.university.order.domain.Money;
import ua.university.order.exception.PaymentException;

public class PayPalPayment implements PaymentMethod {
    @Override
    public void pay(Money amount) {
        if(amount.getAmount() < 300){
            throw new PaymentException("PayPal minimum payment is 300");
        }
    }
}
