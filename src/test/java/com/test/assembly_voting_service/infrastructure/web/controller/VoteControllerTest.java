package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.application.usecase.CastVoteUseCase;
import com.test.assembly_voting_service.application.usecase.GetVotingResultUseCase;
import com.test.assembly_voting_service.application.usecase.command.CastVoteCommand;
import com.test.assembly_voting_service.application.usecase.command.GetVotingResultQuery;
import com.test.assembly_voting_service.domain.model.vote.Vote;
import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import com.test.assembly_voting_service.infrastructure.web.dto.request.CastVoteRequest;
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
class VoteControllerTest {

    @Mock
    private CastVoteUseCase castVoteUseCase;

    @Mock
    private GetVotingResultUseCase getVotingResultUseCase;

    @InjectMocks
    private VoteController controller;

    @Nested
    @DisplayName("POST /api/v1/agendas/{agendaId}/votes")
    class CastVote {

        @Test
        @DisplayName("Deve registrar voto e retornar os dados do voto")
        void shouldCastVoteAndReturnResponse() {
            var agendaId = UUID.randomUUID();
            var memberId = UUID.randomUUID();
            var vote = Vote.create(agendaId, memberId, VoteOption.YES);
            when(castVoteUseCase.execute(any(CastVoteCommand.class))).thenReturn(vote);

            var response = controller.castVote(agendaId, new CastVoteRequest(memberId, VoteOption.YES));

            assertNotNull(response);
            assertEquals(vote.id(), response.id());
            assertEquals(agendaId, response.agendaId());
            assertEquals(memberId, response.memberId());
            assertEquals(VoteOption.YES, response.option());
            assertNotNull(response.createdAt());
            verify(castVoteUseCase).execute(any(CastVoteCommand.class));
        }
    }

    @Nested
    @DisplayName("GET /api/v1/agendas/{agendaId}/result")
    class GetResult {

        @Test
        @DisplayName("Deve retornar resultado da votação com stub zerado")
        void shouldReturnVotingResultStub() {
            var agendaId = UUID.randomUUID();

            var response = controller.getResult(agendaId);

            assertNotNull(response);
            assertEquals(agendaId, response.agendaId());
            assertEquals(0, response.yes());
            assertEquals(0, response.no());
            assertEquals(0, response.total());
            verify(getVotingResultUseCase).execute(any(GetVotingResultQuery.class));
        }
    }
}
