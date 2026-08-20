package com.test.assembly_voting_service.infrastructure.web.dto.request;

import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CastVoteRequest(
        @NotNull(message = "Member ID is required") 
        UUID memberId, 
        
        @NotNull(message = "Vote option is required") 
        VoteOption option
) {
}
