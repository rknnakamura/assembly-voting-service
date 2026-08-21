package com.test.assembly_voting_service.infrastructure.web.dto.request;

import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "Requisição para registrar um voto")
public record CastVoteRequest(
        @Schema(description = "ID do associado que está votando", example = "8dc2dbe4-b5d5-4543-9be2-d7d336b99dc1")
        @NotNull(message = "Member ID is required") 
        UUID memberId, 
        
        @Schema(description = "Opção de voto escolhida pelo associado", example = "YES")
        @NotNull(message = "Vote option is required") 
        VoteOption option
) {
}
