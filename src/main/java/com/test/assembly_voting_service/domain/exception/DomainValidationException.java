package com.test.assembly_voting_service.domain.exception;

public class DomainValidationException extends BusinessException {
    public DomainValidationException(String message) {
        super(message);
    }
}
