package io.ticket.auth.account.application;

import io.ticket.auth.account.application.dto.AuthenticateResult;
import io.ticket.auth.account.infrastructure.AccountApiClient;
import io.ticket.auth.account.infrastructure.dto.AuthenticateRequest;
import io.ticket.auth.account.infrastructure.dto.AuthenticateResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

  private final AccountApiClient accountService;

  public AccountService(final AccountApiClient accountService) {
    this.accountService = accountService;
  }

  public Mono<AuthenticateResult> authenticate(final String username, final String password) {
    return accountService
        .authenticate(new AuthenticateRequest(username, password))
        .map(this::mapAuthenticateResponse);
  }

  private AuthenticateResult mapAuthenticateResponse(final AuthenticateResponse response) {
    return new AuthenticateResult(response.identity(), response.authenticated());
  }
}
