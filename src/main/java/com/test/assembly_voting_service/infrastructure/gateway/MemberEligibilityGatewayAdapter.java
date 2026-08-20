package com.test.assembly_voting_service.infrastructure.gateway;

import com.test.assembly_voting_service.application.port.out.MemberEligibilityGateway;
import org.springframework.stereotype.Component;

@Component
public class MemberEligibilityGatewayAdapter implements MemberEligibilityGateway {

    @Override
    public void validateEligibility(String cpf) {
        // TODO: integração com serviço externo de elegibilidade
    }
}
