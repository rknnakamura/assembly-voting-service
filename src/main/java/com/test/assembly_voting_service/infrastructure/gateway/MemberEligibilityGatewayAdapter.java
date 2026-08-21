package com.test.assembly_voting_service.infrastructure.gateway;

import com.test.assembly_voting_service.application.port.out.MemberEligibilityGateway;
import com.test.assembly_voting_service.infrastructure.integration.userinfo.UserInfoClient;
import org.springframework.stereotype.Component;

@Component
public class MemberEligibilityGatewayAdapter implements MemberEligibilityGateway {

    private final UserInfoClient userInfoClient;

    public MemberEligibilityGatewayAdapter(UserInfoClient userInfoClient) {
        this.userInfoClient = userInfoClient;
    }

    @Override
    public boolean isEligible(String cpf) {
        var response = userInfoClient.checkEligibility(cpf);
        return "ABLE_TO_VOTE".equals(response.status());
    }
}
