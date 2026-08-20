package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.MemberEligibilityGateway;
import com.test.assembly_voting_service.application.port.out.MemberRepository;
import com.test.assembly_voting_service.application.port.out.VoteRepository;
import com.test.assembly_voting_service.application.port.out.VotingSessionRepository;
import com.test.assembly_voting_service.application.usecase.command.CastVoteCommand;
import com.test.assembly_voting_service.domain.exception.BusinessException;
import com.test.assembly_voting_service.domain.exception.ResourceNotFoundException;
import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import com.test.assembly_voting_service.domain.model.member.Member;
import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CastVoteUseCaseTest {

    @Mock
    private VotingSessionRepository votingSessionRepository;
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberEligibilityGateway memberEligibilityGateway;

    @InjectMocks
    private CastVoteUseCase useCase;

    @Test
    void shouldCastVoteSuccessfully() {
        var agendaId = UUID.randomUUID();
        var memberId = UUID.randomUUID();
        var command = new CastVoteCommand(agendaId, memberId, VoteOption.YES);
        var session = VotingSession.create(agendaId, 10);
        var member = new Member(memberId, "12345678901");

        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.of(session));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        doNothing().when(memberEligibilityGateway).validateEligibility(member.cpf());
        when(voteRepository.existsByAgendaIdAndMemberId(agendaId, memberId)).thenReturn(false);
        when(voteRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        var result = useCase.execute(command);

        assertNotNull(result);
        assertEquals(VoteOption.YES, result.option());
        verify(voteRepository).save(any());
    }

    @Test
    void shouldThrowWhenSessionNotFound() {
        var command = new CastVoteCommand(UUID.randomUUID(), UUID.randomUUID(), VoteOption.YES);
        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowWhenSessionIsClosed() {
        var agendaId = UUID.randomUUID();
        var command = new CastVoteCommand(agendaId, UUID.randomUUID(), VoteOption.YES);
        var past = java.time.OffsetDateTime.now().minusMinutes(10);
        var closedSession = new VotingSession(UUID.randomUUID(), agendaId, past, past.plusMinutes(5));

        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.of(closedSession));

        var ex = assertThrows(BusinessException.class, () -> useCase.execute(command));
        assertEquals("Voting session is closed", ex.getMessage());
    }

    @Test
    void shouldThrowWhenMemberAlreadyVoted() {
        var agendaId = UUID.randomUUID();
        var memberId = UUID.randomUUID();
        var command = new CastVoteCommand(agendaId, memberId, VoteOption.YES);
        var session = VotingSession.create(agendaId, 10);
        var member = new Member(memberId, "12345678901");

        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.of(session));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        doNothing().when(memberEligibilityGateway).validateEligibility(member.cpf());
        when(voteRepository.existsByAgendaIdAndMemberId(agendaId, memberId)).thenReturn(true);

        var ex = assertThrows(BusinessException.class, () -> useCase.execute(command));
        assertEquals("Member has already voted on this agenda", ex.getMessage());
    }

    @Test
    void shouldThrowWhenMemberNotFound() {
        var agendaId = UUID.randomUUID();
        var memberId = UUID.randomUUID();
        var command = new CastVoteCommand(agendaId, memberId, VoteOption.YES);
        var session = VotingSession.create(agendaId, 10);

        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.of(session));
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () -> useCase.execute(command));
        assertEquals("Member not found", ex.getMessage());
    }
}
