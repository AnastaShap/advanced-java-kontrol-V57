package ua.university.order.domain;

public record Email(String address) {

    public Email {
        if (!address.contains("@")) throw new IllegalArgumentException("Invalid email");
    }

}
