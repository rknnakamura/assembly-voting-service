package com.test.assembly_voting_service.domain.model.member;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record Member(UUID id, String cpf) {

    public Member {
        requireNonNull(id, "id must not be null");
        requireNonNull(cpf, "cpf must not be null");

        if (cpf.isBlank()) {
            throw new IllegalArgumentException("cpf must not be blank");
        }
    }
}
