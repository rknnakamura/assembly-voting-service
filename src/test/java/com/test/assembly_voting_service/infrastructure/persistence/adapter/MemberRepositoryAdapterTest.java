package com.test.assembly_voting_service.infrastructure.persistence.adapter;

import com.test.assembly_voting_service.infrastructure.persistence.entity.MemberEntity;
import com.test.assembly_voting_service.infrastructure.persistence.repository.SpringDataMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberRepositoryAdapterTest {

    @Mock
    private SpringDataMemberRepository repository;

    @InjectMocks
    private MemberRepositoryAdapter adapter;

    @Test
    void shouldFindMemberById() {
        var memberId = UUID.randomUUID();
        var entity = MemberEntity.builder()
                .id(memberId)
                .cpf("12345678901")
                .build();

        when(repository.findById(memberId)).thenReturn(Optional.of(entity));

        var result = adapter.findById(memberId);

        assertTrue(result.isPresent());
        assertEquals(memberId, result.get().id());
        assertEquals("12345678901", result.get().cpf());
        verify(repository).findById(memberId);
    }
}
