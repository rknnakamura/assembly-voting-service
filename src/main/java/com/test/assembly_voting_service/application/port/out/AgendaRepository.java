package com.test.assembly_voting_service.application.port.out;

import com.test.assembly_voting_service.domain.model.agenda.Agenda;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgendaRepository {
    Agenda save(Agenda agenda);
    List<Agenda> findAll();
    Optional<Agenda> findById(UUID id);
}
