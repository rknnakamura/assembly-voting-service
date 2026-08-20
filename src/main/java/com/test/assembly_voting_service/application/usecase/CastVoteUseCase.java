package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.MemberEligibilityGateway;
import com.test.assembly_voting_service.application.port.out.MemberRepository;
import com.test.assembly_voting_service.application.port.out.VoteRepository;
import com.test.assembly_voting_service.application.port.out.VotingSessionRepository;
import com.test.assembly_voting_service.application.usecase.command.CastVoteCommand;
import com.test.assembly_voting_service.domain.model.vote.Vote;

public class CastVoteUseCase {

    private final VotingSessionRepository votingSessionRepository;
    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final MemberEligibilityGateway memberEligibilityGateway;

    public CastVoteUseCase(
            VotingSessionRepository votingSessionRepository,
            VoteRepository voteRepository,
            MemberRepository memberRepository,
            MemberEligibilityGateway memberEligibilityGateway) {
        this.votingSessionRepository = votingSessionRepository;
        this.voteRepository = voteRepository;
        this.memberRepository = memberRepository;
        this.memberEligibilityGateway = memberEligibilityGateway;
    }

    public Vote execute(CastVoteCommand command) {
        var session = votingSessionRepository.findByAgendaId(command.agendaId())
                .orElseThrow(() -> new IllegalArgumentException("Voting session not found"));

        if (!session.isOpen()) {
            throw new IllegalStateException("Voting session is closed");
        }

        var member = memberRepository.findById(command.memberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        memberEligibilityGateway.validateEligibility(member.cpf());

        if (voteRepository.existsByAgendaIdAndMemberId(command.agendaId(), command.memberId())) {
            throw new IllegalStateException("Member has already voted on this agenda");
        }

        var vote = Vote.create(command.agendaId(), command.memberId(), command.option());
        return voteRepository.save(vote);
    }
}
