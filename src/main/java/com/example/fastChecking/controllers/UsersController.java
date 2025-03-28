package com.example.fastChecking.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.example.fastChecking.dto.UserInfosDto;
import com.example.fastChecking.entities.User;
import com.example.fastChecking.exceptions.AppException;
import com.example.fastChecking.repositories.UserRepository;
import com.example.fastChecking.services.UserService;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
public class UsersController {

  private final UserRepository UserRepository;
  private final UserService UserService;

  @GetMapping("/user")
  public ResponseEntity<UserInfosDto> getUserInfos() {
    UUID userId = UserService.getUserId();
    User user = UserRepository.findById(userId)
        .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

    UserInfosDto userInfosDto =
        UserInfosDto.builder().firstName(user.getFirstName()).lastName(user.getLastName())
            .accounts(user.getAccounts()).categories(user.getCategories()).build();

    return ResponseEntity.ok(userInfosDto);
  }


}
