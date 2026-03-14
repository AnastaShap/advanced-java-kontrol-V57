package ua.university.order.payment;

import ua.university.order.domain.Money;

public class BankTransferPayment implements PaymentMethod {
    @Override
    public void pay(Money amount) {

        double withFee = amount.getAmount() * 1.02;

        System.out.println("Transfer with fee: " + withFee);
    }
}
