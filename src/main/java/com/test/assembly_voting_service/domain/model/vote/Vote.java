package com.test.assembly_voting_service.domain.model.vote;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.test.assembly_voting_service.domain.validation.DomainValidator.requireNonNull;

public record Vote(UUID id, UUID agendaId, UUID memberId, VoteOption option, OffsetDateTime createdAt) {

    public Vote {
        requireNonNull(id, "id");
        requireNonNull(agendaId, "agendaId");
        requireNonNull(memberId, "memberId");
        requireNonNull(option, "option");
        requireNonNull(createdAt, "createdAt");
    }

    public static Vote create(UUID agendaId, UUID memberId, VoteOption option) {
        return new Vote(UUID.randomUUID(), agendaId, memberId, option, OffsetDateTime.now());
    }
}
