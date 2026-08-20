package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.AgendaRepository;
import com.test.assembly_voting_service.application.port.out.VotingSessionRepository;
import com.test.assembly_voting_service.application.usecase.command.OpenVotingSessionCommand;
import com.test.assembly_voting_service.domain.model.agenda.Agenda;
import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenVotingSessionUseCaseTest {

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @InjectMocks
    private OpenVotingSessionUseCase useCase;

    @Test
    void shouldOpenVotingSessionSuccessfully() {
        var agendaId = UUID.randomUUID();
        var command = new OpenVotingSessionCommand(agendaId, 5);
        var agenda = Agenda.create("Pauta");

        when(agendaRepository.findById(agendaId)).thenReturn(Optional.of(agenda));
        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.empty());
        when(votingSessionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        var result = useCase.execute(command);

        assertNotNull(result);
        assertEquals(agenda.id(), result.agendaId());
        verify(votingSessionRepository).save(any());
    }

    @Test
    void shouldThrowWhenAgendaNotFound() {
        var command = new OpenVotingSessionCommand(UUID.randomUUID(), 5);
        when(agendaRepository.findById(command.agendaId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowWhenSessionAlreadyExists() {
        var agendaId = UUID.randomUUID();
        var command = new OpenVotingSessionCommand(agendaId, 5);
        var agenda = Agenda.create("Pauta");
        var existingSession = VotingSession.create(agendaId, 5);

        when(agendaRepository.findById(agendaId)).thenReturn(Optional.of(agenda));
        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.of(existingSession));

        assertThrows(IllegalStateException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldDefaultToOneMinuteWhenDurationIsNull() {
        var agendaId = UUID.randomUUID();
        var command = new OpenVotingSessionCommand(agendaId, null);
        var agenda = Agenda.create("Pauta");

        when(agendaRepository.findById(agendaId)).thenReturn(Optional.of(agenda));
        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.empty());
        when(votingSessionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        var result = useCase.execute(command);

        assertNotNull(result);
        assertEquals(1, Duration.between(result.startedAt(), result.endedAt()).toMinutes());
        verify(votingSessionRepository).save(any());
    }

    @Test
    void shouldDefaultToOneMinuteWhenDurationIsNegative() {
        var agendaId = UUID.randomUUID();
        var command = new OpenVotingSessionCommand(agendaId, -5);
        var agenda = Agenda.create("Pauta");

        when(agendaRepository.findById(agendaId)).thenReturn(Optional.of(agenda));
        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.empty());
        when(votingSessionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        var result = useCase.execute(command);

        assertNotNull(result);
        assertEquals(1, Duration.between(result.startedAt(), result.endedAt()).toMinutes());
        verify(votingSessionRepository).save(any());
    }
}
