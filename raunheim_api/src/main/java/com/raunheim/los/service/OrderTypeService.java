package com.raunheim.los.service;

import com.raunheim.los.model.OrderType;
import com.raunheim.los.repository.OrderTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderTypeService {

    private final OrderTypeRepository orderTypeRepository;

    public OrderType getOrCreate(String name) {
        var orderType = orderTypeRepository.findByName(name);
        if (orderType != null) {
            return orderType;
        }

        log.info("Create a new order type: {}", name);
        var newOrderType = new OrderType();
        newOrderType.setName(name);
        orderTypeRepository.save(newOrderType);

        return newOrderType;
    }

}
