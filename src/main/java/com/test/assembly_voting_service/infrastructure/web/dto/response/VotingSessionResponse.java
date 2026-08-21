package com.test.assembly_voting_service.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Resposta contendo os dados de uma sessão de votação")
public record VotingSessionResponse(
        @Schema(description = "ID único da sessão", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,
        @Schema(description = "ID da pauta associada", example = "5d11235a-472e-48a5-8e4d-7bc432321aba")
        UUID agendaId,
        @Schema(description = "Data e hora de abertura", example = "2026-08-20T10:00:00Z")
        OffsetDateTime startedAt,
        @Schema(description = "Data e hora de encerramento", example = "2026-08-20T10:10:00Z")
        OffsetDateTime endedAt
) {
}
