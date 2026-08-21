package com.test.assembly_voting_service.infrastructure.web.mapper;

import com.test.assembly_voting_service.application.usecase.command.CreateAgendaCommand;
import com.test.assembly_voting_service.domain.model.agenda.Agenda;
import com.test.assembly_voting_service.infrastructure.web.dto.request.CreateAgendaRequest;
import com.test.assembly_voting_service.infrastructure.web.dto.response.AgendaResponse;

public final class AgendaWebMapper {

    private AgendaWebMapper() {
    }

    public static CreateAgendaCommand toCommand(CreateAgendaRequest request) {
        return new CreateAgendaCommand(request.title());
    }

    public static AgendaResponse toResponse(Agenda agenda) {
        return new AgendaResponse(agenda.id(), agenda.title(), agenda.createdAt());
    }
}
