package com.example.fastChecking.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.nio.CharBuffer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import com.example.fastChecking.dto.CredentialsDto;
import com.example.fastChecking.dto.SignUpDto;
import com.example.fastChecking.dto.UserDto;
import com.example.fastChecking.entities.User;
import com.example.fastChecking.exceptions.AppException;
import com.example.fastChecking.mappers.UserMapper;
import com.example.fastChecking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public UserDto login(CredentialsDto credentialsDto) {

    User user = userRepository.findByLogin(credentialsDto.login())
        .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

    if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
      return userMapper.toUserDto(user);
    }
    throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
  }

  public UserDto register(SignUpDto signUpDto) {

    Optional<User> optionalUser = userRepository.findByLogin(signUpDto.login());
    if (optionalUser.isPresent()) {
      throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
    }

    User user = userMapper.signUpToUser(signUpDto);

    user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
    User savedUser = userRepository.save(user);
    return userMapper.toUserDto(savedUser);
  }

  public UserDto getUserDto() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDto userDto = (UserDto) authentication.getPrincipal();
    return userDto;
  }

  public UUID getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDto userDto = (UserDto) authentication.getPrincipal();
    return userDto.getId();
  }


}
