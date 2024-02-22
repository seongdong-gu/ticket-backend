package io.ticket.gateway.credential.application;

import io.ticket.gateway.credential.application.dto.Passport;
import io.ticket.gateway.credential.infrastructure.CredentialHttpApiClient;
import io.ticket.gateway.credential.infrastructure.dto.PassportRequest;
import io.ticket.gateway.credential.infrastructure.dto.PassportResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CredentialService {

  private final CredentialHttpApiClient accountService;

  public CredentialService(final CredentialHttpApiClient accountService) {
    this.accountService = accountService;
  }

  public Mono<Passport> extractIdentityFromToken(final String token) {
    return accountService
        .getIdentity(new PassportRequest(token))
        .map(this::mapAuthenticateResponse);
  }

  private Passport mapAuthenticateResponse(final PassportResponse response) {
    return new Passport(response.identity());
  }
}
