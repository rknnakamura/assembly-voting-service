package com.test.assembly_voting_service.domain.validation;

import com.test.assembly_voting_service.domain.exception.DomainValidationException;

public final class DomainValidator {

    private DomainValidator() {
    }

    public static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new DomainValidationException(fieldName + " must not be null");
        }
        return value;
    }

    public static String requireNotBlank(String value, String fieldName) {
        requireNonNull(value, fieldName);
        if (value.isBlank()) {
            throw new DomainValidationException(fieldName + " must not be blank");
        }
        return value;
    }

    public static void requireTrue(boolean condition, String message) {
        if (!condition) {
            throw new DomainValidationException(message);
        }
    }
}
