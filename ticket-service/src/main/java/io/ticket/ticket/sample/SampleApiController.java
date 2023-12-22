package io.ticket.ticket.sample;

import io.ticket.ticket.echo.EchoExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleApiController {

  private final EchoExternalService echoExternalService;

  @GetMapping("/ping")
  public String ping() {
    return echoExternalService.ping();
  }
}
