package com.diploma.enums;

import java.util.stream.Stream;

public enum PaymentMethod {

    CASH, TERMINAL, OPEN;

    public static PaymentMethod defineMethod(String value) {
        return Stream.of(values())
                     .filter(v -> v.name().equals(value))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("Payment method not found"));
    }
}
