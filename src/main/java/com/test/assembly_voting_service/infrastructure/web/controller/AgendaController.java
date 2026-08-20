package com.test.assembly_voting_service.infrastructure.web.controller;

import com.test.assembly_voting_service.infrastructure.web.mapper.AgendaWebMapper;
import com.test.assembly_voting_service.application.usecase.CreateAgendaUseCase;
import com.test.assembly_voting_service.application.usecase.ListAgendasUseCase;
import com.test.assembly_voting_service.infrastructure.web.dto.request.CreateAgendaRequest;
import com.test.assembly_voting_service.infrastructure.web.dto.response.AgendaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Agendas", description = "Gerenciamento de pautas da assembleia")
@RestController
@RequestMapping("/api/v1/agendas")
public class AgendaController {

    private final CreateAgendaUseCase createAgendaUseCase;
    private final ListAgendasUseCase listAgendasUseCase;

    public AgendaController(
            CreateAgendaUseCase createAgendaUseCase,
            ListAgendasUseCase listAgendasUseCase) {
        this.createAgendaUseCase = createAgendaUseCase;
        this.listAgendasUseCase = listAgendasUseCase;
    }

    @Operation(summary = "Cadastrar pauta", description = "Cria uma nova pauta para ser votada na assembleia")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio ou integridade de domínio violada")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgendaResponse create(@Valid @RequestBody CreateAgendaRequest request) {
        var command = AgendaWebMapper.toCommand(request);
        var agenda = createAgendaUseCase.execute(command);
        return AgendaWebMapper.toResponse(agenda);
    }

    @Operation(summary = "Listar pautas", description = "Retorna todas as pautas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<AgendaResponse> listAll() {
        return listAgendasUseCase.execute().stream()
                .map(AgendaWebMapper::toResponse)
                .toList();
    }
}
