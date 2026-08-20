package com.test.assembly_voting_service.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "voting_sessions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotingSessionEntity {

    @Id
    private UUID id;
    private UUID agendaId;
    private OffsetDateTime startedAt;
    private OffsetDateTime endedAt;
}
