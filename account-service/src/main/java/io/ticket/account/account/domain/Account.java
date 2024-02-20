package io.ticket.account.account.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Account {

  private Long id;

  private String usernmae;

  private String password;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private LocalDateTime deletedAt;

  Account(
      Long id,
      String usernmae,
      String password,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    this.id = id;
    this.usernmae = usernmae;
    this.password = password;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }

  public static Account of(String usernmae, String password, LocalDateTime joinedAt) {
    return new Account(null, usernmae, password, joinedAt, joinedAt, null);
  }
}
