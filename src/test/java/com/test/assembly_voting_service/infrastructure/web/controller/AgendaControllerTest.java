package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.application.usecase.CreateAgendaUseCase;
import com.test.assembly_voting_service.application.usecase.ListAgendasUseCase;
import com.test.assembly_voting_service.application.usecase.command.CreateAgendaCommand;
import com.test.assembly_voting_service.domain.model.agenda.Agenda;
import com.test.assembly_voting_service.infrastructure.web.dto.request.CreateAgendaRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendaControllerTest {

    @Mock
    private CreateAgendaUseCase createAgendaUseCase;

    @Mock
    private ListAgendasUseCase listAgendasUseCase;

    @InjectMocks
    private AgendaController controller;

    @Nested
    @DisplayName("POST /api/v1/agendas")
    class CreateAgenda {

        @Test
        @DisplayName("Deve criar agenda e retornar os dados da agenda criada")
        void shouldCreateAgendaAndReturnResponse() {
            var agenda = Agenda.create("Pauta de Teste");
            when(createAgendaUseCase.execute(any(CreateAgendaCommand.class))).thenReturn(agenda);

            var response = controller.create(new CreateAgendaRequest("Pauta de Teste"));

            assertNotNull(response);
            assertEquals(agenda.id(), response.id());
            assertEquals("Pauta de Teste", response.title());
            assertNotNull(response.createdAt());
            verify(createAgendaUseCase).execute(any(CreateAgendaCommand.class));
        }
    }

    @Nested
    @DisplayName("GET /api/v1/agendas")
    class ListAgendas {

        @Test
        @DisplayName("Deve retornar lista de agendas")
        void shouldReturnListOfAgendas() {
            var agenda1 = Agenda.create("Pauta 1");
            var agenda2 = Agenda.create("Pauta 2");
            when(listAgendasUseCase.execute()).thenReturn(List.of(agenda1, agenda2));

            var response = controller.listAll();

            assertNotNull(response);
            assertEquals(2, response.size());
            assertEquals("Pauta 1", response.get(0).title());
            assertEquals("Pauta 2", response.get(1).title());
            verify(listAgendasUseCase).execute();
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não houver agendas")
        void shouldReturnEmptyListWhenNoAgendas() {
            when(listAgendasUseCase.execute()).thenReturn(List.of());

            var response = controller.listAll();

            assertNotNull(response);
            assertTrue(response.isEmpty());
            verify(listAgendasUseCase).execute();
        }
    }
}
