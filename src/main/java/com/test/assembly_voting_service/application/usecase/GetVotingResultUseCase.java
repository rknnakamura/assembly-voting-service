package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.AppLogger;
import com.test.assembly_voting_service.application.port.out.VoteRepository;
import com.test.assembly_voting_service.application.port.out.VotingSessionRepository;
import com.test.assembly_voting_service.application.usecase.command.GetVotingResultQuery;
import com.test.assembly_voting_service.domain.exception.ResourceNotFoundException;
import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import com.test.assembly_voting_service.domain.model.vote.VotingResult;

public class GetVotingResultUseCase {

    private final VotingSessionRepository votingSessionRepository;
    private final VoteRepository voteRepository;
    private final AppLogger logger;

    public GetVotingResultUseCase(
            VotingSessionRepository votingSessionRepository,
            VoteRepository voteRepository,
            AppLogger logger) {
        this.votingSessionRepository = votingSessionRepository;
        this.voteRepository = voteRepository;
        this.logger = logger;
    }

    public VotingResult execute(GetVotingResultQuery query) {
        logger.info("Voting result requested for agenda ID: {}", query.agendaId());

        votingSessionRepository.findByAgendaId(query.agendaId())
                .orElseThrow(() -> new ResourceNotFoundException("Agenda or voting session not found"));

        var totalYes = voteRepository.countByAgendaIdAndOption(query.agendaId(), VoteOption.YES);
        var totalNo = voteRepository.countByAgendaIdAndOption(query.agendaId(), VoteOption.NO);

        return new VotingResult(query.agendaId(), totalYes, totalNo);
    }
}
