package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.AppLogger;
import com.test.assembly_voting_service.application.port.out.MemberEligibilityGateway;
import com.test.assembly_voting_service.application.port.out.MemberRepository;
import com.test.assembly_voting_service.application.port.out.VoteRepository;
import com.test.assembly_voting_service.application.port.out.VotingSessionRepository;
import com.test.assembly_voting_service.application.usecase.command.CastVoteCommand;
import com.test.assembly_voting_service.domain.exception.BusinessException;
import com.test.assembly_voting_service.domain.exception.ResourceNotFoundException;
import com.test.assembly_voting_service.domain.model.member.Member;
import com.test.assembly_voting_service.domain.model.vote.Vote;
import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Mock
    private AppLogger logger;

    @InjectMocks
    private CastVoteUseCase useCase;

    @Test
    @DisplayName("Deve computar o voto com sucesso")
    void shouldCastVoteSuccessfully() {
        var agendaId = UUID.randomUUID();
        var memberId = UUID.randomUUID();
        var command = new CastVoteCommand(agendaId, memberId, VoteOption.YES);

        var session = VotingSession.create(agendaId, 10);
        var member = new Member(memberId, "12345678901");
        var vote = Vote.create(agendaId, memberId, VoteOption.YES);

        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.of(session));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(memberEligibilityGateway.isEligible(member.cpf())).thenReturn(true);
        when(voteRepository.existsByAgendaIdAndMemberId(agendaId, memberId)).thenReturn(false);
        when(voteRepository.save(any(Vote.class))).thenReturn(vote);

        var result = useCase.execute(command);

        assertNotNull(result);
        assertEquals(agendaId, result.agendaId());
        assertEquals(memberId, result.memberId());
        assertEquals(VoteOption.YES, result.option());
        verify(voteRepository).save(any(Vote.class));
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o associado for inelegível (UNABLE_TO_VOTE)")
    void shouldThrowExceptionWhenMemberIsUnableToVote() {
        var agendaId = UUID.randomUUID();
        var memberId = UUID.randomUUID();
        var command = new CastVoteCommand(agendaId, memberId, VoteOption.YES);

        var session = VotingSession.create(agendaId, 10);
        var member = new Member(memberId, "12345678901");

        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.of(session));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(memberEligibilityGateway.isEligible(member.cpf())).thenReturn(false);

        var exception = assertThrows(BusinessException.class, () -> useCase.execute(command));
        assertEquals("Member is not eligible to vote (UNABLE_TO_VOTE)", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a sessão de votação não existir")
    void shouldThrowExceptionWhenVotingSessionDoesNotExist() {
        var command = new CastVoteCommand(UUID.randomUUID(), UUID.randomUUID(), VoteOption.YES);
        when(votingSessionRepository.findByAgendaId(command.agendaId())).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () -> useCase.execute(command));
        assertEquals("Voting session not found", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a sessão de votação estiver encerrada")
    void shouldThrowExceptionWhenVotingSessionIsClosed() {
        var agendaId = UUID.randomUUID();
        var command = new CastVoteCommand(agendaId, UUID.randomUUID(), VoteOption.YES);
        var session = new VotingSession(
            UUID.randomUUID(), agendaId,
            OffsetDateTime.now().minusMinutes(2),
            OffsetDateTime.now().minusMinutes(1)
        );

        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.of(session));

        var ex = assertThrows(BusinessException.class, () -> useCase.execute(command));
        assertEquals("Voting session is closed", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o associado não existir")
    void shouldThrowExceptionWhenMemberDoesNotExist() {
        var agendaId = UUID.randomUUID();
        var memberId = UUID.randomUUID();
        var command = new CastVoteCommand(agendaId, memberId, VoteOption.YES);
        var session = VotingSession.create(agendaId, 10);

        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.of(session));
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () -> useCase.execute(command));
        assertEquals("Member not found", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o associado já votou na mesma pauta")
    void shouldThrowExceptionWhenMemberAlreadyVoted() {
        var agendaId = UUID.randomUUID();
        var memberId = UUID.randomUUID();
        var command = new CastVoteCommand(agendaId, memberId, VoteOption.YES);

        var session = VotingSession.create(agendaId, 10);
        var member = new Member(memberId, "12345678901");

        when(votingSessionRepository.findByAgendaId(agendaId)).thenReturn(Optional.of(session));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(memberEligibilityGateway.isEligible(member.cpf())).thenReturn(true);
        when(voteRepository.existsByAgendaIdAndMemberId(agendaId, memberId)).thenReturn(true);

        var ex = assertThrows(BusinessException.class, () -> useCase.execute(command));
        assertEquals("Member has already voted on this agenda", ex.getMessage());
    }
}
