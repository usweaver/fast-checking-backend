package com.example.fastChecking.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.example.fastChecking.dto.account.AccountInfosResponseDto;
import com.example.fastChecking.dto.dashboard.DashboardAccountInfosResponseDto;
import com.example.fastChecking.entities.Account;
import com.example.fastChecking.entities.Transaction;
import com.example.fastChecking.exceptions.AppException;
import com.example.fastChecking.repositories.AccountRepository;
import com.example.fastChecking.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountsController {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  @GetMapping("/accounts/dashboard/{accountId}")
  public DashboardAccountInfosResponseDto getDashboardAccountInfos(@PathVariable UUID accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new AppException("Account not found", HttpStatus.NOT_FOUND));

    List<Transaction> recentTransactions =
        transactionRepository.findTop3ByAccountIdOrderByDateDesc(accountId);

    DashboardAccountInfosResponseDto dashboardAccountInfosResponseDto =
        DashboardAccountInfosResponseDto.builder().balance(account.getBalance())
            .recentTransactions(recentTransactions).build();

    return dashboardAccountInfosResponseDto;
  }

  @GetMapping("/accounts/{accountId}/{month}")
  public AccountInfosResponseDto getAccountInfosByMonth(@PathVariable UUID accountId,
      @PathVariable String month) {

    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new AppException("Account not found", HttpStatus.NOT_FOUND));

    List<Transaction> transactions =
        transactionRepository.findByAccountIdAndMonthOrderByDateDesc(accountId, month);

    List<String> months = transactionRepository.findDistinctMonthsByAccountId(accountId);

    AccountInfosResponseDto accountInfosResponseDto = AccountInfosResponseDto.builder()
        .balance(account.getBalance()).months(months).transactions(transactions).build();
    return accountInfosResponseDto;
  }


}
