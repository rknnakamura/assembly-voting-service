package com.test.assembly_voting_service.infrastructure.web.mapper;

import com.test.assembly_voting_service.application.usecase.command.OpenVotingSessionCommand;
import com.test.assembly_voting_service.domain.model.agenda.VotingSession;
import com.test.assembly_voting_service.infrastructure.web.dto.request.OpenVotingSessionRequest;
import com.test.assembly_voting_service.infrastructure.web.dto.response.VotingSessionResponse;

import java.util.UUID;

public final class VotingSessionWebMapper {

    private VotingSessionWebMapper() {
    }

    public static OpenVotingSessionCommand toCommand(UUID agendaId, OpenVotingSessionRequest request) {
        var duration = request != null ? request.durationInMinutes() : null;
        return new OpenVotingSessionCommand(agendaId, duration);
    }

    public static VotingSessionResponse toResponse(VotingSession session) {
        return new VotingSessionResponse(session.id(), session.agendaId(), session.startedAt(), session.endedAt());
    }
}
