package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.AgendaRepository;
import com.test.assembly_voting_service.application.port.out.VotingSessionRepository;
import com.test.assembly_voting_service.application.usecase.command.OpenVotingSessionCommand;
import com.test.assembly_voting_service.domain.exception.BusinessException;
import com.test.assembly_voting_service.domain.exception.ResourceNotFoundException;
import com.test.assembly_voting_service.domain.model.agenda.VotingSession;

public class OpenVotingSessionUseCase {

    private final AgendaRepository agendaRepository;
    private final VotingSessionRepository votingSessionRepository;

    public OpenVotingSessionUseCase(AgendaRepository agendaRepository, VotingSessionRepository votingSessionRepository) {
        this.agendaRepository = agendaRepository;
        this.votingSessionRepository = votingSessionRepository;
    }

    public VotingSession execute(OpenVotingSessionCommand command) {
        var agenda = agendaRepository.findById(command.agendaId())
                .orElseThrow(() -> new ResourceNotFoundException("Agenda not found"));

        votingSessionRepository.findByAgendaId(command.agendaId()).ifPresent(session -> {
            throw new BusinessException("Voting session already exists for this agenda");
        });

        var session = VotingSession.create(agenda.id(), command.durationInMinutes());
        return votingSessionRepository.save(session);
    }
}
