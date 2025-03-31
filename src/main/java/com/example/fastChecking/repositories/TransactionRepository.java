package com.example.fastChecking.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.fastChecking.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId ORDER BY t.date DESC, t.createdAt DESC LIMIT 3")
  List<Transaction> findTop3ByAccountIdOrderByDateDesc(@Param("accountId") UUID accountId);

  @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId AND SUBSTRING(t.date, 1, 7) = :month ORDER BY t.date DESC, t.createdAt DESC")
  List<Transaction> findByAccountIdAndMonthOrderByDateDesc(@Param("accountId") UUID accountId,
      @Param("month") String month);

  @Query("SELECT DISTINCT FUNCTION('SUBSTRING', t.date, 1, 7) FROM Transaction t WHERE t.account.id = :accountId ORDER BY FUNCTION('SUBSTRING', t.date, 1, 7) ASC")
  List<String> findDistinctMonthsByAccountId(@Param("accountId") UUID accountId);

}
