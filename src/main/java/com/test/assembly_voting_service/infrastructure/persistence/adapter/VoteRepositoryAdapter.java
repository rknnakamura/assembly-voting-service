package com.test.assembly_voting_service.infrastructure.persistence.adapter;

import com.test.assembly_voting_service.application.port.out.VoteRepository;
import com.test.assembly_voting_service.domain.model.vote.Vote;
import com.test.assembly_voting_service.infrastructure.persistence.repository.SpringDataVoteRepository;
import com.test.assembly_voting_service.infrastructure.persistence.mapper.VoteMapper;
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
        var entity = VoteMapper.toEntity(vote);
        var saved = repository.save(entity);
        return VoteMapper.toDomain(saved);
    }

    @Override
    public boolean existsByAgendaIdAndMemberId(UUID agendaId, UUID memberId) {
        return repository.existsByAgendaIdAndMemberId(agendaId, memberId);
    }

    @Override
    public long countByAgendaIdAndOption(UUID agendaId, com.test.assembly_voting_service.domain.model.vote.VoteOption option) {
        return repository.countByAgendaIdAndOption(agendaId, option);
    }
}
