package com.test.assembly_voting_service.infrastructure.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Requisição para abertura de sessão de votação")
public record OpenVotingSessionRequest(
        @Schema(description = "Duração da sessão em minutos (padrão: 1 caso não informado)", example = "10")
        Integer durationInMinutes
) {
}
