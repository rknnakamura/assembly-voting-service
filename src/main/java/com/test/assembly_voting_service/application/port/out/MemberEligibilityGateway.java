package com.test.assembly_voting_service.application.port.out;

public interface MemberEligibilityGateway {
    void validateEligibility(String cpf);
}
