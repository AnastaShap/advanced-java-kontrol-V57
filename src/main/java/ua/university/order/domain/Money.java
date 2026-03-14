package ua.university.order.domain;

import java.util.Objects;

public class Money {
    private final double amount;

    public Money(double amount) {
        this.amount = amount;
    }
    public double getAmount() {
        return amount;
    }
    public Money multiply(double quantity) {
        return new Money(amount * quantity);
    }

    public Money add(Money other){
        return new Money(this.amount + other.amount);
    }

    public Money subtract(Money other){
        return new Money(this.amount - other.amount);
    }
    public Money divide(Money other){
        return new Money(this.amount / other.amount);
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                '}';
    }
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Money m))
            return false;
        return Double.compare(amount, m.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(amount);
    }
}
