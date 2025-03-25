package com.example.fastChecking.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fastChecking.entities.User;


public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLogin(String login);

}
