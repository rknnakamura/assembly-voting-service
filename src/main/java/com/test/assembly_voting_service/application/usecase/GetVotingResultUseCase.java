package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.AppLogger;
import com.test.assembly_voting_service.application.usecase.command.GetVotingResultQuery;

public class GetVotingResultUseCase {

    private final AppLogger logger;

    public GetVotingResultUseCase(AppLogger logger) {
        this.logger = logger;
    }

    public Object execute(GetVotingResultQuery query) {
        logger.info("Voting result requested for agenda ID: {}", query.agendaId());
        // TODO: Retornaremos os dados consolidados (contagem de votos e resultado APPROVED/REJECTED)
        return new Object();
    }
}
