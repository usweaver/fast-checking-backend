package com.example.fastChecking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fastChecking.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
