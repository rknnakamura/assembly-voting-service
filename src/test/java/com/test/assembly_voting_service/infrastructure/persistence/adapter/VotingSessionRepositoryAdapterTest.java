package com.test.assembly_voting_service.infrastructure.persistence.adapter;

import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import com.test.assembly_voting_service.infrastructure.persistence.entity.VotingSessionEntity;
import com.test.assembly_voting_service.infrastructure.persistence.repository.SpringDataVotingSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotingSessionRepositoryAdapterTest {

    @Mock
    private SpringDataVotingSessionRepository repository;

    @InjectMocks
    private VotingSessionRepositoryAdapter adapter;

    @Test
    void shouldSaveVotingSession() {
        var session = VotingSession.create(UUID.randomUUID(), 5);
        var entity = VotingSessionEntity.builder()
                .id(session.id())
                .agendaId(session.agendaId())
                .startedAt(session.startedAt())
                .endedAt(session.endedAt())
                .build();

        when(repository.save(any(VotingSessionEntity.class))).thenReturn(entity);

        var result = adapter.save(session);

        assertNotNull(result);
        assertEquals(session.id(), result.id());
        assertEquals(session.agendaId(), result.agendaId());
        verify(repository).save(any(VotingSessionEntity.class));
    }

    @Test
    void shouldFindByAgendaId() {
        var agendaId = UUID.randomUUID();
        var entity = VotingSessionEntity.builder()
                .id(UUID.randomUUID())
                .agendaId(agendaId)
                .startedAt(OffsetDateTime.now())
                .endedAt(OffsetDateTime.now().plusMinutes(5))
                .build();

        when(repository.findByAgendaId(agendaId)).thenReturn(Optional.of(entity));

        var result = adapter.findByAgendaId(agendaId);

        assertTrue(result.isPresent());
        assertEquals(agendaId, result.get().agendaId());
        verify(repository).findByAgendaId(agendaId);
    }
}
