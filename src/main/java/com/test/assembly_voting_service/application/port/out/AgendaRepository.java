package com.test.assembly_voting_service.application.port.out;

import com.test.assembly_voting_service.domain.model.agenda.Agenda;

public interface AgendaRepository {
    Agenda save(Agenda agenda);
}
