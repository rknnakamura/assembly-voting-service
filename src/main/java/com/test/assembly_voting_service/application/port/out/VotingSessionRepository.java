package com.test.assembly_voting_service.application.port.out;

import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import java.util.Optional;
import java.util.UUID;

public interface VotingSessionRepository {
    VotingSession save(VotingSession session);
    Optional<VotingSession> findByAgendaId(UUID agendaId);
}
