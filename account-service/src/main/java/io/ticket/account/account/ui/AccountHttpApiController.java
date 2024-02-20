package io.ticket.account.account.ui;

import io.ticket.account.account.application.AccountService;
import io.ticket.account.account.ui.schema.AccountCreateRequest;
import io.ticket.account.account.ui.schema.AccountCreateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountHttpApiController {

  private final AccountService accountService;

  public AccountHttpApiController(final AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  public ResponseEntity<AccountCreateResponse> createAccount(
      @RequestBody @Validated AccountCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request));
  }
}
