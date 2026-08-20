package com.test.assembly_voting_service.infrastructure.persistence.mapper;

import com.test.assembly_voting_service.domain.model.agenda.Agenda;
import com.test.assembly_voting_service.infrastructure.persistence.entity.AgendaEntity;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AgendaMapperTest {

    @Test
    void shouldMapToEntity() {
        var domain = Agenda.create("Pauta 1");
        var entity = AgendaMapper.toEntity(domain);
        
        assertEquals(domain.id(), entity.getId());
        assertEquals(domain.title(), entity.getTitle());
        assertEquals(domain.createdAt(), entity.getCreatedAt());
    }

    @Test
    void shouldMapToDomain() {
        var entity = AgendaEntity.builder()
                .id(UUID.randomUUID())
                .title("Pauta 2")
                .createdAt(OffsetDateTime.now())
                .build();
                
        var domain = AgendaMapper.toDomain(entity);
        
        assertEquals(entity.getId(), domain.id());
        assertEquals(entity.getTitle(), domain.title());
        assertEquals(entity.getCreatedAt(), domain.createdAt());
    }
}
