package io.ticket.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@RestController
public class EchoApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(EchoApiApplication.class, args);
  }

  @GetMapping("/ping")
  public String ping() {
    return "pong";
  }
}
