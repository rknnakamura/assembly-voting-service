package com.test.assembly_voting_service.infrastructure.persistence.adapter;

import com.test.assembly_voting_service.application.port.out.VoteRepository;
import com.test.assembly_voting_service.domain.model.vote.Vote;
import com.test.assembly_voting_service.infrastructure.persistence.entity.VoteEntity;
import com.test.assembly_voting_service.infrastructure.persistence.repository.SpringDataVoteRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoteRepositoryAdapter implements VoteRepository {

    private final SpringDataVoteRepository repository;

    public VoteRepositoryAdapter(SpringDataVoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vote save(Vote vote) {
        var entity = VoteEntity.builder()
                .id(vote.id())
                .agendaId(vote.agendaId())
                .memberId(vote.memberId())
                .option(vote.option())
                .createdAt(vote.createdAt())
                .build();
        var saved = repository.save(entity);
        return new Vote(saved.getId(), saved.getAgendaId(), saved.getMemberId(), saved.getOption(), saved.getCreatedAt());
    }

    @Override
    public boolean existsByAgendaIdAndMemberId(UUID agendaId, UUID memberId) {
        return repository.existsByAgendaIdAndMemberId(agendaId, memberId);
    }
}
