package com.raunheim.los.config;

import com.raunheim.los.model.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {
    @Override
    public String convertToDatabaseColumn(OrderStatus status) {
        return status.name().toLowerCase();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String value) {
        return OrderStatus.valueOf(value.toUpperCase());
    }
}
