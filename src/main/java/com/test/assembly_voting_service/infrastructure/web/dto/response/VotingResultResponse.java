package com.test.assembly_voting_service.infrastructure.web.dto.response;

import java.util.UUID;

public record VotingResultResponse(UUID agendaId, long yes, long no, long total) {
}
