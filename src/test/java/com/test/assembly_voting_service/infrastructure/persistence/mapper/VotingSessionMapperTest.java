package com.test.assembly_voting_service.infrastructure.persistence.mapper;

import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import com.test.assembly_voting_service.infrastructure.persistence.entity.VotingSessionEntity;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VotingSessionMapperTest {

    @Test
    void shouldMapToEntity() {
        var domain = VotingSession.create(UUID.randomUUID(), 10);
        var entity = VotingSessionMapper.toEntity(domain);
        
        assertEquals(domain.id(), entity.getId());
        assertEquals(domain.agendaId(), entity.getAgendaId());
        assertEquals(domain.startedAt(), entity.getStartedAt());
        assertEquals(domain.endedAt(), entity.getEndedAt());
    }

    @Test
    void shouldMapToDomain() {
        var entity = VotingSessionEntity.builder()
                .id(UUID.randomUUID())
                .agendaId(UUID.randomUUID())
                .startedAt(OffsetDateTime.now())
                .endedAt(OffsetDateTime.now().plusMinutes(5))
                .build();
                
        var domain = VotingSessionMapper.toDomain(entity);
        
        assertEquals(entity.getId(), domain.id());
        assertEquals(entity.getAgendaId(), domain.agendaId());
        assertEquals(entity.getStartedAt(), domain.startedAt());
        assertEquals(entity.getEndedAt(), domain.endedAt());
    }
}
