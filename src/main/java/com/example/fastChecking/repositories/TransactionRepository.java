package com.example.fastChecking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fastChecking.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
