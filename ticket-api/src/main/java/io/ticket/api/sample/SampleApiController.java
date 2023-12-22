package io.ticket.api.sample;

import io.ticket.api.echo.EchoApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleApiController {

  private final EchoApiService echoApiService;

  @GetMapping("/ping")
  public String ping() {
    return echoApiService.ping();
  }
}
