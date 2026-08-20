package com.test.assembly_voting_service.infrastructure.config;

import com.test.assembly_voting_service.application.port.out.AgendaRepository;
import com.test.assembly_voting_service.application.port.out.MemberEligibilityGateway;
import com.test.assembly_voting_service.application.port.out.MemberRepository;
import com.test.assembly_voting_service.application.port.out.VoteRepository;
import com.test.assembly_voting_service.application.port.out.VotingSessionRepository;
import com.test.assembly_voting_service.application.usecase.CastVoteUseCase;
import com.test.assembly_voting_service.application.usecase.CreateAgendaUseCase;
import com.test.assembly_voting_service.application.usecase.GetVotingResultUseCase;
import com.test.assembly_voting_service.application.usecase.ListAgendasUseCase;
import com.test.assembly_voting_service.application.usecase.OpenVotingSessionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public CreateAgendaUseCase createAgendaUseCase(AgendaRepository agendaRepository) {
        return new CreateAgendaUseCase(agendaRepository);
    }

    @Bean
    public ListAgendasUseCase listAgendasUseCase(AgendaRepository agendaRepository) {
        return new ListAgendasUseCase(agendaRepository);
    }

    @Bean
    public OpenVotingSessionUseCase openVotingSessionUseCase(
            AgendaRepository agendaRepository,
            VotingSessionRepository votingSessionRepository) {
        return new OpenVotingSessionUseCase(agendaRepository, votingSessionRepository);
    }

    @Bean
    public CastVoteUseCase castVoteUseCase(
            VotingSessionRepository votingSessionRepository,
            VoteRepository voteRepository,
            MemberRepository memberRepository,
            MemberEligibilityGateway memberEligibilityGateway) {
        return new CastVoteUseCase(votingSessionRepository, voteRepository, memberRepository, memberEligibilityGateway);
    }

    @Bean
    public GetVotingResultUseCase getVotingResultUseCase() {
        return new GetVotingResultUseCase();
    }
}
