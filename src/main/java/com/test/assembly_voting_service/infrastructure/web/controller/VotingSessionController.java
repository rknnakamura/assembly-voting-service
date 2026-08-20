package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.infrastructure.web.mapper.VotingSessionWebMapper;
import com.test.assembly_voting_service.application.usecase.OpenVotingSessionUseCase;
import com.test.assembly_voting_service.infrastructure.web.dto.request.OpenVotingSessionRequest;
import com.test.assembly_voting_service.infrastructure.web.dto.response.VotingSessionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Voting Sessions", description = "Gerenciamento de sessões de votação")
@RestController
@RequestMapping("/api/v1/agendas/{agendaId}/voting-session")
public class VotingSessionController {

    private final OpenVotingSessionUseCase openVotingSessionUseCase;

    public VotingSessionController(OpenVotingSessionUseCase openVotingSessionUseCase) {
        this.openVotingSessionUseCase = openVotingSessionUseCase;
    }

    @Operation(
            summary = "Abrir sessão de votação",
            description = "Abre uma sessão de votação para a pauta informada. A duração padrão é de 1 minuto caso não seja informada.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sessão aberta com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada"),
            @ApiResponse(responseCode = "422", description = "Sessão de votação já existe para esta pauta")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VotingSessionResponse openVotingSession(
            @Parameter(description = "ID da pauta") @PathVariable UUID agendaId,
            @Valid @RequestBody(required = false) OpenVotingSessionRequest request) {
        var command = VotingSessionWebMapper.toCommand(agendaId, request);
        var session = openVotingSessionUseCase.execute(command);
        return VotingSessionWebMapper.toResponse(session);
    }
}
