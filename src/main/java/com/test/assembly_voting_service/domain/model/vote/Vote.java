package com.test.assembly_voting_service.domain.model.vote;

import java.time.OffsetDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record Vote(UUID id, UUID agendaId, UUID memberId, VoteOption option, OffsetDateTime createdAt) {

    public Vote {
        requireNonNull(id, "id must not be null");
        requireNonNull(agendaId, "agendaId must not be null");
        requireNonNull(memberId, "memberId must not be null");
        requireNonNull(option, "option must not be null");
        requireNonNull(createdAt, "createdAt must not be null");
    }
}
