package com.test.assembly_voting_service.domain.model.vote;

import java.util.UUID;

public record VotingResult(UUID agendaId, long totalYes, long totalNo) {

    public long totalVotes() {
        return totalYes + totalNo;
    }

    public VotingStatus status() {
        return totalYes >= totalNo ? VotingStatus.APPROVED : VotingStatus.REPROVED;
    }
}
