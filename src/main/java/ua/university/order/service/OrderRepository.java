package ua.university.order.service;

import ua.university.order.domain.Order;

import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(String id);

}
