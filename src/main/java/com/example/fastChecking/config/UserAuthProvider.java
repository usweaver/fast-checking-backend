package com.example.fastChecking.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.fastChecking.dto.UserDto;
import com.example.fastChecking.entities.User;
import com.example.fastChecking.exceptions.AppException;
import com.example.fastChecking.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  private Algorithm algorithm;

  private final UserRepository userRepository;

  @PostConstruct
  protected void init() {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    String base64Key = Base64.getEncoder().encodeToString(keyBytes);
    this.algorithm = Algorithm.HMAC256(base64Key);
  }

  public String createToken(UserDto dto) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + 3600000);

    return JWT.create().withIssuer(dto.getLogin()).withIssuedAt(now).withExpiresAt(validity)
        .withClaim("firstName", dto.getFirstName()).withClaim("lastName", dto.getLastName())
        .sign(algorithm);
  }

  public Authentication validateToken(String token) {
    try {
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT decoded = verifier.verify(token);

      User user = userRepository.findByLogin(decoded.getIssuer())
          .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

      UserDto userDto = UserDto.builder().id(user.getId()).login(user.getLogin())
          .firstName(user.getFirstName()).lastName(user.getLastName()).build();

      return new UsernamePasswordAuthenticationToken(userDto, null, Collections.emptyList());
    } catch (JWTVerificationException e) {
      throw new AppException("Invalid Token", HttpStatus.UNAUTHORIZED);
    }
  }

}
