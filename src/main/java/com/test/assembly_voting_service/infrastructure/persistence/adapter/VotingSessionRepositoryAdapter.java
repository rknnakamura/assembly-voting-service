package com.test.assembly_voting_service.infrastructure.persistence.adapter;

import com.test.assembly_voting_service.application.port.out.VotingSessionRepository;
import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import com.test.assembly_voting_service.infrastructure.persistence.repository.SpringDataVotingSessionRepository;
import com.test.assembly_voting_service.infrastructure.persistence.mapper.VotingSessionMapper;
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
        var entity = VotingSessionMapper.toEntity(session);
        var saved = repository.save(entity);
        return VotingSessionMapper.toDomain(saved);
    }

    @Override
    public Optional<VotingSession> findByAgendaId(UUID agendaId) {
        return repository.findByAgendaId(agendaId)
                .map(VotingSessionMapper::toDomain);
    }
}
