package com.test.assembly_voting_service.infrastructure.web.dto.request;

import jakarta.validation.constraints.Min;

public record OpenVotingSessionRequest(
        @Min(value = 1, message = "Duration in minutes must be greater than zero") 
        Integer durationInMinutes
) {
}
