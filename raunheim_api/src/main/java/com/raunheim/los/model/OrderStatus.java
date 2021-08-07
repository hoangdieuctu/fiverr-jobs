package com.raunheim.los.model;

public enum OrderStatus {
    NEW,
    UNRESERVED,
    RESERVATION_REQUESTED,
    RESERVED,
    RELEASE_REQUESTED,
    OPEN,
    OPEN_REQUESTED,
    BATCHED,
    IN_PICKING,
    PICKED,
    PICKED_AND_WRITTEN,
    BEFORE_PACKING,
    IN_PACKING,
    AFTER_PACKING,
    SHIPPED,
    DELETED;
}
