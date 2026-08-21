package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.AgendaRepository;
import com.test.assembly_voting_service.application.port.out.AppLogger;
import com.test.assembly_voting_service.application.usecase.command.CreateAgendaCommand;
import com.test.assembly_voting_service.domain.model.agenda.Agenda;

public class CreateAgendaUseCase {

    private final AgendaRepository agendaRepository;
    private final AppLogger logger;

    public CreateAgendaUseCase(AgendaRepository agendaRepository, AppLogger logger) {
        this.agendaRepository = agendaRepository;
        this.logger = logger;
    }

    public Agenda execute(CreateAgendaCommand command) {
        var agenda = Agenda.create(command.title());
        var savedAgenda = agendaRepository.save(agenda);
        logger.info("Agenda created successfully with ID: {}", savedAgenda.id());
        return savedAgenda;
    }
}
