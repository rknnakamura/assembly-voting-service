package com.test.assembly_voting_service.infrastructure.web.dto.response;

import java.time.OffsetDateTime;

public record ApiErrorResponse(
        OffsetDateTime timestamp,
        String message
) {
}
