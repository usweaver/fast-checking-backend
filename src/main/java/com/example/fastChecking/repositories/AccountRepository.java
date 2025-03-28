package com.example.fastChecking.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fastChecking.entities.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {

}
