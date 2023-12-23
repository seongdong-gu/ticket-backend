package io.ticket.ticket.echo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "echo-service")
public interface EchoExternalService {

  @GetMapping(value = "/ping", consumes = "application/json")
  String ping();
}
