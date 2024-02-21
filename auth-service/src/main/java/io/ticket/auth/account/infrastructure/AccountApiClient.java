package io.ticket.auth.account.infrastructure;

import io.ticket.auth.account.infrastructure.dto.AuthenticateRequest;
import io.ticket.auth.account.infrastructure.dto.AuthenticateResponse;
import io.ticket.common.schema.HttpApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AccountApiClient {

  private final WebClient.Builder webClient;

  public AccountApiClient() {
    this.webClient = WebClient.builder();
  }

  public Mono<AuthenticateResponse> authenticate(final AuthenticateRequest request) {
    return webClient
        .build()
        .post()
        .uri("http://localhost:8080/authenticate")
        .bodyValue(request)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<HttpApiResponse<AuthenticateResponse>>() {})
        .map(HttpApiResponse::data);
  }
}
