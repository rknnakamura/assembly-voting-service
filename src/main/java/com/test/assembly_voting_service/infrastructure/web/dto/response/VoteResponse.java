package com.test.assembly_voting_service.infrastructure.web.dto.response;

import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import java.time.OffsetDateTime;
import java.util.UUID;

public record VoteResponse(UUID id, UUID agendaId, UUID memberId, VoteOption option, OffsetDateTime createdAt) {
}
