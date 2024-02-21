package io.ticket.auth.passport.utility;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JsonWebTokenUtility {

  private final SecretKey secretKey;

  private final long tokenExpirationMsec;

  public JsonWebTokenUtility(
      @Value("${app.jwt.secret}") final String secretKey,
      @Value("${app.jwt.tokenExpirationMsec}") final long tokenExpirationMsec) {
    this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    this.tokenExpirationMsec = tokenExpirationMsec;
  }

  public String createToken(final String subject) {
    final Date now = Date.from(new Date().toInstant());
    final Date validity = new Date(now.getTime() + tokenExpirationMsec);

    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(validity)
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
        .signWith(secretKey)
        .compact();
  }

  public String parseToken(final String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateToken(final String token) {
    try {
      Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
