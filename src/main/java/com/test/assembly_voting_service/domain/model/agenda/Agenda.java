package com.test.assembly_voting_service.domain.model.agenda;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.test.assembly_voting_service.domain.validation.DomainValidator.requireNotBlank;
import static com.test.assembly_voting_service.domain.validation.DomainValidator.requireNonNull;

public record Agenda(UUID id, String title, OffsetDateTime createdAt) {

    public Agenda {
        requireNonNull(id, "id");
        requireNotBlank(title, "title");
        requireNonNull(createdAt, "createdAt");
    }

    public static Agenda create(String title) {
        return new Agenda(UUID.randomUUID(), title, OffsetDateTime.now());
    }
}
