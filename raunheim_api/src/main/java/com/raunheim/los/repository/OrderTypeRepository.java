package com.raunheim.los.repository;

import com.raunheim.los.model.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTypeRepository extends JpaRepository<OrderType, Integer> {
    OrderType findByName(String name);
}
