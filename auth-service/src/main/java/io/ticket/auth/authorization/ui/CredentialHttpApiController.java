package io.ticket.auth.authorization.ui;

import io.ticket.auth.authorization.application.AuthorizationService;
import io.ticket.auth.authorization.ui.schema.PassportAuthenticateRequest;
import io.ticket.auth.authorization.ui.schema.PassportAuthenticateResponse;
import io.ticket.auth.authorization.ui.schema.PasswordAuthenticateRequest;
import io.ticket.auth.authorization.ui.schema.PasswordAuthenticateResponse;
import io.ticket.common.schema.HttpApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
public class CredentialHttpApiController {

  private final AuthorizationService authorizationService;

  public CredentialHttpApiController(final AuthorizationService authorizationService) {
    this.authorizationService = authorizationService;
  }

  @PostMapping("/passport")
  public Mono<HttpApiResponse<PassportAuthenticateResponse>> authenticatePassport(
      @RequestBody final PassportAuthenticateRequest request) {
    return authorizationService
        .extractIdentityFromToken(request.passport())
        .map(HttpApiResponse::ok);
  }

  @PostMapping
  public Mono<HttpApiResponse<PasswordAuthenticateResponse>> authenticate(
      @RequestBody final PasswordAuthenticateRequest request) {
    return authorizationService
        .validateCredentials(request.username(), request.password())
        .map(HttpApiResponse::ok);
  }
}
