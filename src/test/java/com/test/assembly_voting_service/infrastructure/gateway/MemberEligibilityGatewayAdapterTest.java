package com.test.assembly_voting_service.infrastructure.gateway;

import com.test.assembly_voting_service.infrastructure.integration.userinfo.UserInfoClient;
import com.test.assembly_voting_service.infrastructure.integration.userinfo.UserInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberEligibilityGatewayAdapterTest {

    @Mock
    private UserInfoClient userInfoClient;

    @InjectMocks
    private MemberEligibilityGatewayAdapter gateway;

    @Test
    @DisplayName("Deve retornar true quando status for ABLE_TO_VOTE")
    void shouldReturnTrueWhenAbleToVote() {
        var cpf = "12345678901";
        when(userInfoClient.checkEligibility(cpf)).thenReturn(new UserInfoResponse("ABLE_TO_VOTE"));

        var result = gateway.isEligible(cpf);

        assertTrue(result);
    }

    @Test
    @DisplayName("Deve retornar false quando status for UNABLE_TO_VOTE")
    void shouldReturnFalseWhenUnableToVote() {
        var cpf = "12345678901";
        when(userInfoClient.checkEligibility(cpf)).thenReturn(new UserInfoResponse("UNABLE_TO_VOTE"));

        var result = gateway.isEligible(cpf);

        assertFalse(result);
    }
}
