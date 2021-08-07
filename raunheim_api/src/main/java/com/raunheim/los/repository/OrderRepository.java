package com.raunheim.los.repository;

import com.raunheim.los.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderId(String orderId);
}
