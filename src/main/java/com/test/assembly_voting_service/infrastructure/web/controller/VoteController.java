package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.application.usecase.CastVoteUseCase;
import com.test.assembly_voting_service.application.usecase.GetVotingResultUseCase;
import com.test.assembly_voting_service.application.usecase.command.CastVoteCommand;
import com.test.assembly_voting_service.application.usecase.command.GetVotingResultQuery;
import com.test.assembly_voting_service.infrastructure.web.dto.request.CastVoteRequest;
import com.test.assembly_voting_service.infrastructure.web.dto.response.VoteResponse;
import com.test.assembly_voting_service.infrastructure.web.dto.response.VotingResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Votes", description = "Registro e consulta de votos")
@RestController
@RequestMapping("/api/v1/agendas/{agendaId}")
public class VoteController {

    private final CastVoteUseCase castVoteUseCase;
    private final GetVotingResultUseCase getVotingResultUseCase;

    public VoteController(
            CastVoteUseCase castVoteUseCase,
            GetVotingResultUseCase getVotingResultUseCase) {
        this.castVoteUseCase = castVoteUseCase;
        this.getVotingResultUseCase = getVotingResultUseCase;
    }

    @Operation(summary = "Registrar voto", description = "Registra o voto de um associado em uma pauta com sessão aberta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Sessão de votação ou associado não encontrado"),
            @ApiResponse(responseCode = "422", description = "Sessão encerrada ou associado já votou nesta pauta")
    })
    @PostMapping("/votes")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteResponse castVote(
            @Parameter(description = "ID da pauta") @PathVariable UUID agendaId,
            @Valid @RequestBody CastVoteRequest request) {
        var vote = castVoteUseCase.execute(new CastVoteCommand(agendaId, request.memberId(), request.option()));
        return new VoteResponse(vote.id(), vote.agendaId(), vote.memberId(), vote.option(), vote.createdAt());
    }

    @Operation(summary = "Consultar resultado", description = "Retorna o resultado consolidado da votação de uma pauta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
    })
    @GetMapping("/result")
    public VotingResultResponse getResult(
            @Parameter(description = "ID da pauta") @PathVariable UUID agendaId) {
        getVotingResultUseCase.execute(new GetVotingResultQuery(agendaId));
        return new VotingResultResponse(agendaId, 0, 0, 0);
    }
}
