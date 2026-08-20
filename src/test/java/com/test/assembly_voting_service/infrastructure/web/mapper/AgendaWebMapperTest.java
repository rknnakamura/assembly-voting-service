package com.test.assembly_voting_service.infrastructure.web.mapper;

import com.test.assembly_voting_service.domain.model.agenda.Agenda;
import com.test.assembly_voting_service.infrastructure.web.dto.request.CreateAgendaRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AgendaWebMapperTest {

    @Test
    void shouldMapToCommand() {
        var request = new CreateAgendaRequest("Pauta Teste");

        var command = AgendaWebMapper.toCommand(request);

        assertNotNull(command);
        assertEquals("Pauta Teste", command.title());
    }

    @Test
    void shouldMapToResponse() {
        var agenda = Agenda.create("Pauta Teste");

        var response = AgendaWebMapper.toResponse(agenda);

        assertNotNull(response);
        assertEquals(agenda.id(), response.id());
        assertEquals("Pauta Teste", response.title());
        assertEquals(agenda.createdAt(), response.createdAt());
    }
}
