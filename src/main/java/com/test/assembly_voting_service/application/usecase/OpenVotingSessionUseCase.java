package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.AgendaRepository;
import com.test.assembly_voting_service.application.port.out.AppLogger;
import com.test.assembly_voting_service.application.port.out.VotingSessionRepository;
import com.test.assembly_voting_service.application.usecase.command.OpenVotingSessionCommand;
import com.test.assembly_voting_service.domain.exception.BusinessException;
import com.test.assembly_voting_service.domain.exception.ResourceNotFoundException;
import com.test.assembly_voting_service.domain.model.agenda.VotingSession;

public class OpenVotingSessionUseCase {

    private final AgendaRepository agendaRepository;
    private final VotingSessionRepository votingSessionRepository;
    private final AppLogger logger;

    public OpenVotingSessionUseCase(
            AgendaRepository agendaRepository,
            VotingSessionRepository votingSessionRepository,
            AppLogger logger) {
        this.agendaRepository = agendaRepository;
        this.votingSessionRepository = votingSessionRepository;
        this.logger = logger;
    }

    public VotingSession execute(OpenVotingSessionCommand command) {
        var agenda = agendaRepository.findById(command.agendaId())
                .orElseThrow(() -> new ResourceNotFoundException("Agenda not found"));

        votingSessionRepository.findByAgendaId(command.agendaId()).ifPresent(session -> {
            throw new BusinessException("Voting session already exists for this agenda");
        });

        var session = VotingSession.create(agenda.id(), command.durationInMinutes());
        var savedSession = votingSessionRepository.save(session);
        logger.info("Voting session opened successfully for agenda ID: {}", agenda.id());
        return savedSession;
    }
}
