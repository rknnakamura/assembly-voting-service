package com.test.assembly_voting_service.application.usecase.command;

import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import java.util.UUID;

public record CastVoteCommand(UUID agendaId, UUID memberId, VoteOption option) {
}
