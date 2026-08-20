package com.test.assembly_voting_service.infrastructure.persistence.repository;

import com.test.assembly_voting_service.infrastructure.persistence.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataVoteRepository extends JpaRepository<VoteEntity, UUID> {
    boolean existsByAgendaIdAndMemberId(UUID agendaId, UUID memberId);
}
