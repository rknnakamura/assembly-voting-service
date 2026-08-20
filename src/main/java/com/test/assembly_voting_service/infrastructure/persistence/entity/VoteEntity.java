package com.test.assembly_voting_service.infrastructure.persistence.entity;

import com.test.assembly_voting_service.domain.model.vote.VoteOption;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "vote")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteEntity {

    @Id
    private UUID id;
    private UUID agendaId;
    private UUID memberId;

    @Enumerated(EnumType.STRING)
    private VoteOption option;

    private OffsetDateTime createdAt;
}
