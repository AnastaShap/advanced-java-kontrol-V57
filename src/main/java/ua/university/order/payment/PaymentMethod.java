package ua.university.order.payment;

import ua.university.order.domain.Money;

public interface PaymentMethod {
    void pay(Money amount);
}
