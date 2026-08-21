package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.AppLogger;
import com.test.assembly_voting_service.application.port.out.VoteRepository;
import com.test.assembly_voting_service.application.port.out.VotingSessionRepository;
import com.test.assembly_voting_service.application.usecase.command.GetVotingResultQuery;
import com.test.assembly_voting_service.domain.exception.ResourceNotFoundException;
import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetVotingResultUseCaseTest {

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private AppLogger logger;

    @InjectMocks
    private GetVotingResultUseCase useCase;

    @Test
    @DisplayName("Deve retornar o resultado consolidado da votação")
    void shouldReturnVotingResultSuccessfully() {
        var agendaId = UUID.randomUUID();
        var query = new GetVotingResultQuery(agendaId);
        var session = VotingSession.create(agendaId, 10);

        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.of(session));
        when(voteRepository.countByAgendaIdAndOption(agendaId, VoteOption.YES)).thenReturn(10L);
        when(voteRepository.countByAgendaIdAndOption(agendaId, VoteOption.NO)).thenReturn(5L);

        var result = useCase.execute(query);

        assertNotNull(result);
        assertEquals(agendaId, result.agendaId());
        assertEquals(10L, result.totalYes());
        assertEquals(5L, result.totalNo());
        assertEquals(15L, result.totalVotes());
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando a pauta/sessão não existir")
    void shouldThrowResourceNotFoundExceptionWhenAgendaDoesNotExist() {
        var agendaId = UUID.randomUUID();
        var query = new GetVotingResultQuery(agendaId);

        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () -> useCase.execute(query));
        assertEquals("Agenda or voting session not found", exception.getMessage());
    }
}
