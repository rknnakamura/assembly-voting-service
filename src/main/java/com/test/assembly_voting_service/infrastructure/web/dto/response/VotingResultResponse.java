package com.test.assembly_voting_service.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resultado consolidado da votação de uma pauta")
public record VotingResultResponse(
        @Schema(description = "ID da pauta", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID agendaId,
        @Schema(description = "Total de votos a favor (Sim)", example = "150")
        long totalYes,
        @Schema(description = "Total de votos contra (Não)", example = "30")
        long totalNo,
        @Schema(description = "Total de votos computados", example = "180")
        long totalVotes,
        @Schema(description = "Resultado final da votação (APPROVED ou REPROVED)", example = "APPROVED")
        String status
) {
}
