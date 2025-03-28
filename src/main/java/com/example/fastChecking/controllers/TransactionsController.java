package com.example.fastChecking.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.example.fastChecking.dto.transactions.AddTransactionRequestDto;
import com.example.fastChecking.entities.Account;
import com.example.fastChecking.entities.Category;
import com.example.fastChecking.entities.Transaction;
import com.example.fastChecking.exceptions.AppException;
import com.example.fastChecking.repositories.AccountRepository;
import com.example.fastChecking.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionsController {

  private final AccountRepository AccountRepository;
  private final CategoryRepository CategoryRepository;
  private final com.example.fastChecking.repositories.TransactionRepository TransactionRepository;

  @PostMapping("/transactions")
  public ResponseEntity<Transaction> createTransaction(
      @RequestBody AddTransactionRequestDto entity) {

    Account account = AccountRepository.findById(entity.getAccountId())
        .orElseThrow(() -> new AppException("Account not found", HttpStatus.NOT_FOUND));

    Category category = CategoryRepository.findById(entity.getCategoryId())
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

    AccountRepository.save(account);

    Transaction savedTransaction = TransactionRepository.save(newTransaction);

    return ResponseEntity.ok(savedTransaction);
  }


}
