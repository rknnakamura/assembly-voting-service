package com.test.assembly_voting_service.domain.model.agenda;

import java.time.OffsetDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record VotingSession(UUID id, UUID agendaId, OffsetDateTime startedAt, OffsetDateTime endedAt) {

    public VotingSession {
        requireNonNull(id, "id must not be null");
        requireNonNull(agendaId, "agendaId must not be null");
        requireNonNull(startedAt, "startedAt must not be null");
        requireNonNull(endedAt, "endedAt must not be null");

        if (!endedAt.isAfter(startedAt)) {
            throw new IllegalArgumentException("endedAt must be after startedAt");
        }
    }

    public boolean isOpen() {
        var now = OffsetDateTime.now();
        return !now.isBefore(startedAt) && now.isBefore(endedAt);
    }
}
