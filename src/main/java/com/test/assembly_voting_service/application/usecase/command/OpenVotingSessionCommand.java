package com.test.assembly_voting_service.application.usecase.command;

import java.util.UUID;

public record OpenVotingSessionCommand(UUID agendaId, Integer durationInMinutes) {
}
