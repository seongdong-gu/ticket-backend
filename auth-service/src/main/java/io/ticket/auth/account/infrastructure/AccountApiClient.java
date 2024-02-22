package io.ticket.auth.account.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ticket.auth.account.infrastructure.dto.AuthenticateRequest;
import io.ticket.auth.account.infrastructure.dto.AuthenticateResponse;
import io.ticket.common.schema.HttpApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AccountApiClient {

  private final WebClient.Builder webClient;

  private final ObjectMapper objectMapper;

  public AccountApiClient(final WebClient.Builder webClient, final ObjectMapper objectMapper) {
    this.webClient = webClient;
    this.objectMapper = objectMapper;
  }

  public Mono<AuthenticateResponse> authenticate(final AuthenticateRequest request) {
    return webClient
        .build()
        .post()
        .uri("http://ACCOUNT-SERVICE/api/v1/accounts/authenticate")
        .bodyValue(request)
        .retrieve()
        .bodyToMono(HttpApiResponse.class)
        .handle(
            (spec, sink) -> {
              final Object data = spec.data();
              if (spec.status() == HttpStatus.OK.value()) {
                final AuthenticateResponse response =
                    objectMapper.convertValue(data, AuthenticateResponse.class);
                sink.next(response);
                return;
              }
              sink.error(new IllegalStateException("Invalid response"));
            });
  }
}
