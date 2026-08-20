package com.test.assembly_voting_service.domain.model.agenda;

import java.time.OffsetDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record Agenda(UUID id, String title, OffsetDateTime createdAt) {

    public Agenda {
        requireNonNull(id, "id must not be null");
        requireNonNull(title, "title must not be null");
        requireNonNull(createdAt, "createdAt must not be null");

        if (title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
    }

    public static Agenda create(String title) {
        return new Agenda(UUID.randomUUID(), title, OffsetDateTime.now());
    }
}
