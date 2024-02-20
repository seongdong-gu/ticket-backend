package io.ticket.account.account.repository;

import io.ticket.account.account.domain.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {

  Account selectAccountByUsername(String username);

  void insertAccount(Account account);
}
