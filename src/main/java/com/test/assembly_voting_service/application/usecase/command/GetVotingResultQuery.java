package com.test.assembly_voting_service.application.usecase.command;

import java.util.UUID;

public record GetVotingResultQuery(UUID agendaId) {
}
