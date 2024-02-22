package io.ticket.gateway.filter;

import io.ticket.gateway.credential.application.CredentialService;
import io.ticket.gateway.credential.application.dto.Passport;
import java.util.Objects;
import java.util.function.Function;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalAuthorizationFilter implements GlobalFilter {

  private final CredentialService credentialService;

  public GlobalAuthorizationFilter(CredentialService credentialService) {
    this.credentialService = credentialService;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String token = exchange.getRequest().getHeaders().getFirst("Authorization");
    if (Objects.isNull(token)) {
      return chain.filter(exchange);
    }

    return credentialService
        .extractIdentityFromToken(token)
        .flatMap(addIdentityHeader(exchange, chain));
  }

  private static Function<Passport, Mono<? extends Void>> addIdentityHeader(
      ServerWebExchange exchange, GatewayFilterChain chain) {
    return passport -> {
      exchange
          .getRequest()
          .mutate()
          .header("X-Identity", String.valueOf(passport.identity()))
          .build();
      return chain.filter(exchange);
    };
  }
}
