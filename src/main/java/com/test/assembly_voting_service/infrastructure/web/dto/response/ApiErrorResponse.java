package com.test.assembly_voting_service.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "Formato padronizado para retorno de erros na API")
public record ApiErrorResponse(
        @Schema(description = "Data e hora do erro", example = "2026-08-20T10:00:00Z")
        OffsetDateTime timestamp,
        @Schema(description = "Mensagem descritiva do erro", example = "Membro não elegível para votar")
        String message
) {
}
