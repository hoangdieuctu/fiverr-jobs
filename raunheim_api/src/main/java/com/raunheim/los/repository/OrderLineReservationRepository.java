package com.raunheim.los.repository;

import com.raunheim.los.model.OrderLineReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineReservationRepository extends JpaRepository<OrderLineReservation, Long> {
    List<OrderLineReservation> findByOrderLineId(Long orderLineId);

    List<OrderLineReservation> findByOrderLineIdAndPickList(Long orderLineId, String pickList);
}
