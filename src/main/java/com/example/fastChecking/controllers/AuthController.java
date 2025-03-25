package com.example.fastChecking.controllers;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.fastChecking.config.UserAuthProvider;
import com.example.fastChecking.dto.CredentialsDto;
import com.example.fastChecking.dto.SignUpDto;
import com.example.fastChecking.dto.UserDto;
import com.example.fastChecking.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;
  private final UserAuthProvider userAuthProvider;

  @PostMapping("/login")
  public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialDto) {
    UserDto user = userService.login(credentialDto);
    user.setToken(userAuthProvider.createToken(user));
    return ResponseEntity.ok(user);
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
    UserDto user = userService.register(signUpDto);
    user.setToken(userAuthProvider.createToken(user));
    return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
  }


}
