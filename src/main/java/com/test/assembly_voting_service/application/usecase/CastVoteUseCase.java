package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.MemberEligibilityGateway;
import com.test.assembly_voting_service.application.port.out.MemberRepository;
import com.test.assembly_voting_service.application.port.out.VoteRepository;
import com.test.assembly_voting_service.application.port.out.VotingSessionRepository;
import com.test.assembly_voting_service.application.port.out.AppLogger;
import com.test.assembly_voting_service.application.usecase.command.CastVoteCommand;
import com.test.assembly_voting_service.domain.exception.BusinessException;
import com.test.assembly_voting_service.domain.exception.ResourceNotFoundException;
import com.test.assembly_voting_service.domain.model.vote.Vote;

public class CastVoteUseCase {

    private final VotingSessionRepository votingSessionRepository;
    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final MemberEligibilityGateway memberEligibilityGateway;
    private final AppLogger logger;

    public CastVoteUseCase(
            VotingSessionRepository votingSessionRepository,
            VoteRepository voteRepository,
            MemberRepository memberRepository,
            MemberEligibilityGateway memberEligibilityGateway,
            AppLogger logger) {
        this.votingSessionRepository = votingSessionRepository;
        this.voteRepository = voteRepository;
        this.memberRepository = memberRepository;
        this.memberEligibilityGateway = memberEligibilityGateway;
        this.logger = logger;
    }

    public Vote execute(CastVoteCommand command) {
        var session = votingSessionRepository.findByAgendaId(command.agendaId())
                .orElseThrow(() -> new ResourceNotFoundException("Voting session not found"));

        if (!session.isOpen()) {
            throw new BusinessException("Voting session is closed");
        }

        var member = memberRepository.findById(command.memberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        memberEligibilityGateway.validateEligibility(member.cpf());

        if (voteRepository.existsByAgendaIdAndMemberId(command.agendaId(), command.memberId())) {
            throw new BusinessException("Member has already voted on this agenda");
        }

        var vote = Vote.create(command.agendaId(), command.memberId(), command.option());
        var savedVote = voteRepository.save(vote);
        logger.info("Vote cast successfully for agenda ID: {} by member ID: {}", command.agendaId(), command.memberId());
        return savedVote;
    }
}
