package com.test.assembly_voting_service.infrastructure.persistence.mapper;

import com.test.assembly_voting_service.infrastructure.persistence.entity.MemberEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberMapperTest {

    @Test
    void shouldMapToDomain() {
        var entity = MemberEntity.builder()
                .id(UUID.randomUUID())
                .cpf("09876543210")
                .build();
                
        var domain = MemberMapper.toDomain(entity);
        
        assertEquals(entity.getId(), domain.id());
        assertEquals(entity.getCpf(), domain.cpf());
    }
}
