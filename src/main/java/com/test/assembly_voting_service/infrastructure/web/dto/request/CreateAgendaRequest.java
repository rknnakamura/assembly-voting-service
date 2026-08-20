package com.test.assembly_voting_service.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateAgendaRequest(
        @NotBlank(message = "Title is required") 
        String title
) {
}
