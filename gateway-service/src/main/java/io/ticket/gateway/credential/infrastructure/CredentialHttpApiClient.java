package io.ticket.gateway.credential.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ticket.common.schema.HttpApiResponse;
import io.ticket.gateway.credential.infrastructure.dto.PassportRequest;
import io.ticket.gateway.credential.infrastructure.dto.PassportResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CredentialHttpApiClient {

  private final WebClient.Builder webClient;

  private final ObjectMapper objectMapper;

  public CredentialHttpApiClient(
      final WebClient.Builder webClient, final ObjectMapper objectMapper) {
    this.webClient = webClient;
    this.objectMapper = objectMapper;
  }

  public Mono<PassportResponse> getIdentity(final PassportRequest request) {
    return webClient
        .build()
        .post()
        .uri("http://AUTH-SERVICE/api/v1/auth/passport")
        .bodyValue(request)
        .retrieve()
        .bodyToMono(HttpApiResponse.class)
        .handle(
            (spec, sink) -> {
              final Object data = spec.data();
              if (spec.status() == HttpStatus.OK.value()) {
                final PassportResponse response =
                    objectMapper.convertValue(data, PassportResponse.class);
                sink.next(response);
                return;
              }
              sink.error(new IllegalStateException("Invalid response"));
            });
  }
}
