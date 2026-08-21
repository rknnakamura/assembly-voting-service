package com.test.assembly_voting_service.infrastructure.persistence.adapter;

import com.test.assembly_voting_service.domain.model.agenda.Agenda;
import com.test.assembly_voting_service.infrastructure.persistence.entity.AgendaEntity;
import com.test.assembly_voting_service.infrastructure.persistence.repository.SpringDataAgendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendaRepositoryAdapterTest {

    @Mock
    private SpringDataAgendaRepository repository;

    @InjectMocks
    private AgendaRepositoryAdapter adapter;

    @Test
    void shouldSaveAgenda() {
        var agenda = Agenda.create("Pauta de Teste");
        var entity = AgendaEntity.builder()
                .id(agenda.id())
                .title(agenda.title())
                .createdAt(agenda.createdAt())
                .build();

        when(repository.save(any(AgendaEntity.class))).thenReturn(entity);

        var result = adapter.save(agenda);

        assertNotNull(result);
        assertEquals(agenda.id(), result.id());
        assertEquals(agenda.title(), result.title());
        verify(repository).save(any(AgendaEntity.class));
    }

    @Test
    void shouldFindAll() {
        var entity = AgendaEntity.builder()
                .id(UUID.randomUUID())
                .title("Pauta 1")
                .createdAt(OffsetDateTime.now())
                .build();

        when(repository.findAll()).thenReturn(List.of(entity));

        var results = adapter.findAll();

        assertFalse(results.isEmpty());
        assertEquals(entity.getId(), results.getFirst().id());
        verify(repository).findAll();
    }

    @Test
    void shouldFindById() {
        var id = UUID.randomUUID();
        var entity = AgendaEntity.builder()
                .id(id)
                .title("Pauta Buscada")
                .createdAt(OffsetDateTime.now())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        var result = adapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().id());
        verify(repository).findById(id);
    }
}
