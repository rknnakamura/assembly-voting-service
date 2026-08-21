package com.test.assembly_voting_service.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Resposta contendo os dados de uma pauta")
public record AgendaResponse(
        @Schema(description = "ID único da pauta", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,
        @Schema(description = "Título da pauta", example = "Eleição para síndico 2026")
        String title,
        @Schema(description = "Data e hora em que a pauta foi criada", example = "2026-08-20T10:00:00Z")
        OffsetDateTime createdAt
) {
}
