package com.test.assembly_voting_service.infrastructure.web.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AgendaResponse(UUID id, String title, OffsetDateTime createdAt) {
}
