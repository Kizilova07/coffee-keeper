package com.diploma.enums;

import java.util.stream.Stream;

public enum UserRole {

    ADMIN, USER;

    public static UserRole defineRole(String value) {
        return Stream.of(values())
                     .filter(v -> v.name().equals(value))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("User role not found"));
    }
}
