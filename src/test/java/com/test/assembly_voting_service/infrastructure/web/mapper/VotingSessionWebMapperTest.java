package com.test.assembly_voting_service.infrastructure.web.mapper;

import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import com.test.assembly_voting_service.infrastructure.web.dto.request.OpenVotingSessionRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class VotingSessionWebMapperTest {

    @Test
    void shouldMapToCommandWithDuration() {
        var agendaId = UUID.randomUUID();
        var request = new OpenVotingSessionRequest(10);

        var command = VotingSessionWebMapper.toCommand(agendaId, request);

        assertNotNull(command);
        assertEquals(agendaId, command.agendaId());
        assertEquals(10, command.durationInMinutes());
    }

    @Test
    void shouldMapToCommandWithNullRequest() {
        var agendaId = UUID.randomUUID();

        var command = VotingSessionWebMapper.toCommand(agendaId, null);

        assertNotNull(command);
        assertEquals(agendaId, command.agendaId());
        assertNull(command.durationInMinutes());
    }

    @Test
    void shouldMapToResponse() {
        var session = VotingSession.create(UUID.randomUUID(), 5);

        var response = VotingSessionWebMapper.toResponse(session);

        assertNotNull(response);
        assertEquals(session.id(), response.id());
        assertEquals(session.agendaId(), response.agendaId());
        assertEquals(session.startedAt(), response.startedAt());
        assertEquals(session.endedAt(), response.endedAt());
    }
}
