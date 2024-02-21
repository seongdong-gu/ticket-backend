package io.ticket.auth.passport.application;

import io.ticket.auth.passport.utility.JsonWebTokenUtility;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class PassportManager {

  private final JsonWebTokenUtility jsonWebTokenUtility;

  public PassportManager(JsonWebTokenUtility jsonWebTokenUtility) {
    this.jsonWebTokenUtility = jsonWebTokenUtility;
  }

  public String issuePassportToken(final long identity) {
    return jsonWebTokenUtility.createToken(String.valueOf(identity));
  }

  public long extractIdentity(final String token) {
    if (Objects.isNull(token) || jsonWebTokenUtility.validateToken(token)) {
      throw new IllegalArgumentException("Invalid token");
    }

    return Long.parseLong(jsonWebTokenUtility.parseToken(token));
  }
}
