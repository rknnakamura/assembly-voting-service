package com.test.assembly_voting_service.infrastructure.persistence.repository;

import com.test.assembly_voting_service.infrastructure.persistence.entity.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataAgendaRepository extends JpaRepository<AgendaEntity, UUID> {
}
