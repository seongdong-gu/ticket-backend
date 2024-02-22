package io.ticket.account.account.application;

import io.ticket.account.account.domain.Account;
import io.ticket.account.account.repository.AccountMapper;
import io.ticket.account.account.ui.schema.AccountAuthenticateRequest;
import io.ticket.account.account.ui.schema.AccountAuthenticateResponse;
import io.ticket.account.account.ui.schema.AccountCreateRequest;
import io.ticket.account.account.ui.schema.AccountCreateResponse;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccountService {

  private final AccountMapper accountMapper;

  public AccountService(AccountMapper accountMapper) {
    this.accountMapper = accountMapper;
  }

  @Transactional
  public AccountCreateResponse createAccount(final AccountCreateRequest request) {
    if (accountMapper.existsByUsername(request.username())) {
      throw new IllegalArgumentException("Username is already exists");
    }

    // 성능 측정을 목적으로 하는 구현체라 추가적인 보안 작업을 수행하지 않습니다.
    accountMapper.insertAccount(
        Account.of(request.username(), request.password(), LocalDateTime.now()));

    final Account account = accountMapper.selectAccountByUsername(request.username());
    return new AccountCreateResponse(account.getId());
  }

  public AccountAuthenticateResponse authenticate(final AccountAuthenticateRequest request) {
    if (!accountMapper.existsByUsername(request.username())) {
      throw new IllegalArgumentException("Username is not exists");
    }

    final Account account = accountMapper.selectAccountByUsername(request.username());
    if (!account.matchPassword(request.password())) {
      throw new IllegalArgumentException("Invalid password");
    }

    return new AccountAuthenticateResponse(account.getId());
  }
}
