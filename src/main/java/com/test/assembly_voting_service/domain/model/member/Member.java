package com.test.assembly_voting_service.domain.model.member;

import java.util.UUID;

import static com.test.assembly_voting_service.domain.validation.DomainValidator.requireNotBlank;
import static com.test.assembly_voting_service.domain.validation.DomainValidator.requireNonNull;

public record Member(UUID id, String cpf) {

    public Member {
        requireNonNull(id, "id");
        requireNotBlank(cpf, "cpf");
    }
}
