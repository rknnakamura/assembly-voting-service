package com.test.assembly_voting_service.infrastructure.persistence.adapter;

import com.test.assembly_voting_service.application.port.out.VotingSessionRepository;
import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import com.test.assembly_voting_service.infrastructure.persistence.entity.VotingSessionEntity;
import com.test.assembly_voting_service.infrastructure.persistence.repository.SpringDataVotingSessionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class VotingSessionRepositoryAdapter implements VotingSessionRepository {

    private final SpringDataVotingSessionRepository repository;

    public VotingSessionRepositoryAdapter(SpringDataVotingSessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public VotingSession save(VotingSession session) {
        var entity = VotingSessionEntity.builder()
                .id(session.id())
                .agendaId(session.agendaId())
                .startedAt(session.startedAt())
                .endedAt(session.endedAt())
                .build();
        var saved = repository.save(entity);
        return new VotingSession(saved.getId(), saved.getAgendaId(), saved.getStartedAt(), saved.getEndedAt());
    }

    @Override
    public Optional<VotingSession> findByAgendaId(UUID agendaId) {
        return repository.findByAgendaId(agendaId)
                .map(e -> new VotingSession(e.getId(), e.getAgendaId(), e.getStartedAt(), e.getEndedAt()));
    }
}
