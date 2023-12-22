package io.ticket.api.echo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "echo-api")
public interface EchoApiService {

  @GetMapping(value = "/ping", consumes = "application/json")
  String ping();
}
