package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.application.usecase.OpenVotingSessionUseCase;
import com.test.assembly_voting_service.application.usecase.command.OpenVotingSessionCommand;
import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import com.test.assembly_voting_service.infrastructure.web.dto.request.OpenVotingSessionRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotingSessionControllerTest {

    @Mock
    private OpenVotingSessionUseCase openVotingSessionUseCase;

    @InjectMocks
    private VotingSessionController controller;

    @Nested
    @DisplayName("POST /api/v1/agendas/{agendaId}/voting-session")
    class OpenVotingSession {

        @Test
        @DisplayName("Deve abrir sessão com duração informada")
        void shouldOpenVotingSessionWithDuration() {
            var agendaId = UUID.randomUUID();
            var session = VotingSession.create(agendaId, 10);
            when(openVotingSessionUseCase.execute(any(OpenVotingSessionCommand.class))).thenReturn(session);

            var response = controller.openVotingSession(agendaId, new OpenVotingSessionRequest(10));

            assertNotNull(response);
            assertEquals(session.id(), response.id());
            assertEquals(agendaId, response.agendaId());
            assertNotNull(response.startedAt());
            assertNotNull(response.endedAt());
            verify(openVotingSessionUseCase).execute(any(OpenVotingSessionCommand.class));
        }

        @Test
        @DisplayName("Deve abrir sessão com duração padrão quando body não for informado")
        void shouldOpenVotingSessionWithDefaultDurationWhenRequestIsNull() {
            var agendaId = UUID.randomUUID();
            var session = VotingSession.create(agendaId, null);
            when(openVotingSessionUseCase.execute(any(OpenVotingSessionCommand.class))).thenReturn(session);

            var response = controller.openVotingSession(agendaId, null);

            assertNotNull(response);
            assertEquals(agendaId, response.agendaId());
            verify(openVotingSessionUseCase).execute(any(OpenVotingSessionCommand.class));
        }
    }
}
