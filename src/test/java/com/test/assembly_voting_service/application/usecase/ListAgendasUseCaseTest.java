package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.AgendaRepository;
import com.test.assembly_voting_service.domain.model.agenda.Agenda;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListAgendasUseCaseTest {

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private ListAgendasUseCase listAgendasUseCase;

    @Test
    @DisplayName("Deve listar todas as agendas")
    void shouldListAllAgendas() {
        var agenda1 = new Agenda(UUID.randomUUID(), "Pauta 1", OffsetDateTime.now());
        var agenda2 = new Agenda(UUID.randomUUID(), "Pauta 2", OffsetDateTime.now());
        var expectedAgendas = List.of(agenda1, agenda2);

        when(agendaRepository.findAll()).thenReturn(expectedAgendas);

        var result = listAgendasUseCase.execute();

        assertEquals(2, result.size());
        assertEquals(expectedAgendas, result);
        
        verify(agendaRepository, times(1)).findAll();
    }
}
