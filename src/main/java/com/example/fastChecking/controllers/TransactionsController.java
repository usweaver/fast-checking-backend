package com.example.fastChecking.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.example.fastChecking.dto.transactions.AddTransactionRequestDto;
import com.example.fastChecking.entities.Account;
import com.example.fastChecking.entities.Category;
import com.example.fastChecking.entities.Transaction;
import com.example.fastChecking.exceptions.AppException;
import com.example.fastChecking.repositories.AccountRepository;
import com.example.fastChecking.repositories.CategoryRepository;
import com.example.fastChecking.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionsController {

  private final AccountRepository accountRepository;
  private final CategoryRepository categoryRepository;
  private final TransactionRepository transactionRepository;

  @PostMapping("/transactions")
  public ResponseEntity<Transaction> createTransaction(
      @RequestBody AddTransactionRequestDto entity) {

    Account account = accountRepository.findById(entity.getAccountId())
        .orElseThrow(() -> new AppException("Account not found", HttpStatus.NOT_FOUND));

    Category category = categoryRepository.findById(entity.getCategoryId())
        .orElseThrow(() -> new AppException("Category not found", HttpStatus.NOT_FOUND));

    Transaction newTransaction =
        Transaction.builder().name(entity.getName()).amount(entity.getAmount())
            .date(entity.getDate()).type(entity.getType()).checked(entity.getChecked())
            .regularization(entity.getRegularization()).category(category).account(account).build();

    if (newTransaction.getType().equals("debit")) {
      account.setBalance(account.getBalance() - newTransaction.getAmount());
    } else {
      account.setBalance(account.getBalance() + newTransaction.getAmount());
    } ;

    accountRepository.save(account);

    Transaction savedTransaction = transactionRepository.save(newTransaction);

    return ResponseEntity.ok(savedTransaction);
  }

  @DeleteMapping("/transactions/{transactionId}")
  public ResponseEntity<Transaction> deleteTransaction(@PathVariable UUID transactionId) {

    Transaction transaction = transactionRepository.findById(transactionId)
        .orElseThrow(() -> new AppException("Transaction not found", HttpStatus.NOT_FOUND));

    Account account = accountRepository.findById(transaction.getAccount().getId())
        .orElseThrow(() -> new AppException("Account not found", HttpStatus.NOT_FOUND));

    if (transaction.getType().equals("debit")) {
      account.setBalance(account.getBalance() + transaction.getAmount());
    } else {
      account.setBalance(account.getBalance() - transaction.getAmount());
    } ;

    accountRepository.save(account);

    transactionRepository.delete(transaction);

    return ResponseEntity.ok(transaction);
  }

  @GetMapping("/transactions/{accountId}/{month}")
  public List<Transaction> getTransactionsByMonth(@PathVariable UUID accountId,
      @PathVariable String month) {


    List<Transaction> transactions =
        transactionRepository.findByAccountIdAndMonthOrderByDateDesc(accountId, month);

    return transactions;
  }



}
