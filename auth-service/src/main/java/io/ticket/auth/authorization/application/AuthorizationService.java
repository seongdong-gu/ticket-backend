package io.ticket.auth.authorization.application;

import io.ticket.auth.account.application.AccountService;
import io.ticket.auth.authorization.ui.schema.PasswordAuthenticateResponse;
import io.ticket.auth.passport.application.PassportManager;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthorizationService {

  private final AccountService accountService;

  private final PassportManager passportManager;

  public AuthorizationService(
      final AccountService accountService, final PassportManager passportManager) {
    this.accountService = accountService;
    this.passportManager = passportManager;
  }

  public Mono<PasswordAuthenticateResponse> validateCredentials(
      final String username, final String password) {
    return accountService
        .authenticate(username, password)
        .handle(
            (result, sink) -> {
              if (!result.authenticated()) {
                sink.error(new IllegalStateException("Invalid credentials"));
                return;
              }

              final String passport = passportManager.issuePassportToken(result.identity());
              sink.next(new PasswordAuthenticateResponse(passport));
            });
  }
}
