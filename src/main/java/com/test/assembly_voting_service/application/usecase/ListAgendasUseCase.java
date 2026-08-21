package com.test.assembly_voting_service.application.usecase;

import com.test.assembly_voting_service.application.port.out.AgendaRepository;
import com.test.assembly_voting_service.domain.model.agenda.Agenda;

import java.util.List;

public class ListAgendasUseCase {

    private final AgendaRepository agendaRepository;

    public ListAgendasUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public List<Agenda> execute() {
        return agendaRepository.findAll();
    }
}
