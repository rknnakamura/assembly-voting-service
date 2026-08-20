package com.test.assembly_voting_service.infrastructure.persistence.repository;

import com.test.assembly_voting_service.infrastructure.persistence.entity.VotingSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataVotingSessionRepository extends JpaRepository<VotingSessionEntity, UUID> {
    Optional<VotingSessionEntity> findByAgendaId(UUID agendaId);
}
