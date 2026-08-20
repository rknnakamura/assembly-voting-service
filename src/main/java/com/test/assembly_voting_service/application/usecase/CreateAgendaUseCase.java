package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.AgendaRepository;
import com.test.assembly_voting_service.application.usecase.command.CreateAgendaCommand;
import com.test.assembly_voting_service.domain.model.agenda.Agenda;

public class CreateAgendaUseCase {

    private final AgendaRepository agendaRepository;

    public CreateAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Agenda execute(CreateAgendaCommand command) {
        var agenda = Agenda.create(command.title());
        return agendaRepository.save(agenda);
    }
}
