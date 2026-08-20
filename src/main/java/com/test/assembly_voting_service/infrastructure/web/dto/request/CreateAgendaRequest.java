package com.test.assembly_voting_service.infrastructure.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Requisição para criação de uma nova pauta")
public record CreateAgendaRequest(
        @Schema(description = "Título descritivo da pauta", example = "Eleição para síndico 2026")
        @NotBlank(message = "Title is required") 
        String title
) {
}
