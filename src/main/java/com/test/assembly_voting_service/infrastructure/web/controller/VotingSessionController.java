package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.application.usecase.OpenVotingSessionUseCase;
import com.test.assembly_voting_service.application.usecase.command.OpenVotingSessionCommand;
import com.test.assembly_voting_service.infrastructure.web.dto.request.OpenVotingSessionRequest;
import com.test.assembly_voting_service.infrastructure.web.dto.response.VotingSessionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agendas/{agendaId}/voting-session")
public class VotingSessionController {

    private final OpenVotingSessionUseCase openVotingSessionUseCase;

    public VotingSessionController(OpenVotingSessionUseCase openVotingSessionUseCase) {
        this.openVotingSessionUseCase = openVotingSessionUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VotingSessionResponse openVotingSession(
            @PathVariable UUID agendaId,
            @RequestBody(required = false) OpenVotingSessionRequest request) {
        // Trata caso a requisição venha sem body (vazia) já que duration é opcional
        Integer duration = request != null ? request.durationInMinutes() : null;
        var session = openVotingSessionUseCase.execute(new OpenVotingSessionCommand(agendaId, duration));
        return new VotingSessionResponse(session.id(), session.agendaId(), session.startedAt(), session.endedAt());
    }
}
