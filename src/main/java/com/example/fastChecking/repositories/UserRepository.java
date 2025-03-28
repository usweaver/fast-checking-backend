package com.example.fastChecking.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fastChecking.entities.User;


public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByLogin(String login);

}
