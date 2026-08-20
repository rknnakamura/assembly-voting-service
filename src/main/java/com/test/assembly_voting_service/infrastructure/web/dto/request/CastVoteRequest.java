package com.test.assembly_voting_service.infrastructure.web.dto.request;

import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import java.util.UUID;

public record CastVoteRequest(UUID memberId, VoteOption option) {
}
