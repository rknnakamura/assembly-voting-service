package com.test.assembly_voting_service.application.port.out;

import com.test.assembly_voting_service.domain.model.vote.Vote;
import java.util.UUID;

public interface VoteRepository {
    Vote save(Vote vote);
    boolean existsByAgendaIdAndMemberId(UUID agendaId, UUID memberId);
}
