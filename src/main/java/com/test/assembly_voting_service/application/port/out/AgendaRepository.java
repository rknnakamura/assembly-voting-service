package com.test.assembly_voting_service.application.port.out;

import com.test.assembly_voting_service.domain.model.agenda.Agenda;

import java.util.List;

public interface AgendaRepository {
    Agenda save(Agenda agenda);
    List<Agenda> findAll();
}
