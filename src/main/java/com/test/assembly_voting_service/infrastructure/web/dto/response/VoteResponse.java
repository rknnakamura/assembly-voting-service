package com.test.assembly_voting_service.infrastructure.web.dto.response;

import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Resposta contendo os dados do voto registrado")
public record VoteResponse(
        @Schema(description = "ID único do voto", example = "e00845a7-9610-449b-ad7c-7d9382fbfaf0")
        UUID id,
        @Schema(description = "ID da pauta votada", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID agendaId,
        @Schema(description = "ID do associado", example = "8dc2dbe4-b5d5-4543-9be2-d7d336b99dc1")
        UUID memberId,
        @Schema(description = "Opção escolhida", example = "YES")
        VoteOption option,
        @Schema(description = "Data e hora em que o voto foi registrado", example = "2026-08-20T10:05:00Z")
        OffsetDateTime createdAt
) {
}
