package com.test.assembly_voting_service.infrastructure.persistence.mapper;

import com.test.assembly_voting_service.domain.model.vote.Vote;
import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import com.test.assembly_voting_service.infrastructure.persistence.entity.VoteEntity;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VoteMapperTest {

    @Test
    void shouldMapToEntity() {
        var domain = Vote.create(UUID.randomUUID(), UUID.randomUUID(), VoteOption.YES);
        var entity = VoteMapper.toEntity(domain);
        
        assertEquals(domain.id(), entity.getId());
        assertEquals(domain.agendaId(), entity.getAgendaId());
        assertEquals(domain.memberId(), entity.getMemberId());
        assertEquals(domain.option(), entity.getOption());
        assertEquals(domain.createdAt(), entity.getCreatedAt());
    }

    @Test
    void shouldMapToDomain() {
        var entity = VoteEntity.builder()
                .id(UUID.randomUUID())
                .agendaId(UUID.randomUUID())
                .memberId(UUID.randomUUID())
                .option(VoteOption.NO)
                .createdAt(OffsetDateTime.now())
                .build();
                
        var domain = VoteMapper.toDomain(entity);
        
        assertEquals(entity.getId(), domain.id());
        assertEquals(entity.getAgendaId(), domain.agendaId());
        assertEquals(entity.getMemberId(), domain.memberId());
        assertEquals(entity.getOption(), domain.option());
        assertEquals(entity.getCreatedAt(), domain.createdAt());
    }
}
