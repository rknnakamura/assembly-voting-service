package com.test.assembly_voting_service.infrastructure.persistence.adapter;

import com.test.assembly_voting_service.domain.model.vote.Vote;
import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import com.test.assembly_voting_service.infrastructure.persistence.entity.VoteEntity;
import com.test.assembly_voting_service.infrastructure.persistence.repository.SpringDataVoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteRepositoryAdapterTest {

    @Mock
    private SpringDataVoteRepository repository;

    @InjectMocks
    private VoteRepositoryAdapter adapter;

    @Test
    void shouldSaveVote() {
        var vote = Vote.create(UUID.randomUUID(), UUID.randomUUID(), VoteOption.YES);
        var entity = VoteEntity.builder()
                .id(vote.id())
                .agendaId(vote.agendaId())
                .memberId(vote.memberId())
                .option(vote.option())
                .createdAt(vote.createdAt())
                .build();

        when(repository.save(any(VoteEntity.class))).thenReturn(entity);

        var result = adapter.save(vote);

        assertNotNull(result);
        assertEquals(vote.id(), result.id());
        assertEquals(VoteOption.YES, result.option());
        verify(repository).save(any(VoteEntity.class));
    }

    @Test
    void shouldReturnTrueWhenVoteExists() {
        var agendaId = UUID.randomUUID();
        var memberId = UUID.randomUUID();

        when(repository.existsByAgendaIdAndMemberId(agendaId, memberId)).thenReturn(true);

        var exists = adapter.existsByAgendaIdAndMemberId(agendaId, memberId);

        assertTrue(exists);
        verify(repository).existsByAgendaIdAndMemberId(agendaId, memberId);
    }
    @Test
    void shouldReturnCountByOption() {
        var agendaId = UUID.randomUUID();

        when(repository.countByAgendaIdAndOption(agendaId, VoteOption.YES)).thenReturn(10L);

        var count = adapter.countByAgendaIdAndOption(agendaId, VoteOption.YES);

        assertEquals(10L, count);
        verify(repository).countByAgendaIdAndOption(agendaId, VoteOption.YES);
    }
}
