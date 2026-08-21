package com.test.assembly_voting_service.application.port.out;

import com.test.assembly_voting_service.domain.model.vote.Vote;
import com.test.assembly_voting_service.domain.model.vote.VoteOption;

import java.util.UUID;

public interface VoteRepository {
    Vote save(Vote vote);
    boolean existsByAgendaIdAndMemberId(UUID agendaId, UUID memberId);
    long countByAgendaIdAndOption(UUID agendaId, VoteOption option);
}
