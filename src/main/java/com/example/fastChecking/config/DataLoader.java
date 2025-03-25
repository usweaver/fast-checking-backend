package com.example.fastChecking.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.fastChecking.entities.Account;
import com.example.fastChecking.entities.Category;
import com.example.fastChecking.entities.Transaction;
import com.example.fastChecking.entities.User;
import com.example.fastChecking.repositories.AccountRepository;
import com.example.fastChecking.repositories.CategoryRepository;
import com.example.fastChecking.repositories.TransactionRepository;
import com.example.fastChecking.repositories.UserRepository;

@Slf4j
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AccountRepository accountRepository;
  private final CategoryRepository categoryRepository;
  private final TransactionRepository transactionRepository;

  @Override
  public void run(String... args) {
    log.info("Start loading datas...");

    List<User> users = createUsers();
    log.info("Number of users created: {}", users.size());
    List<Account> accounts = createAccounts(users);
    log.info("Number of accounts created: {}", accounts.size());
    List<Category> categories = createCategories(users);
    log.info("Number of categories created: {}", categories.size());
    List<Transaction> transactions = createTransactions(accounts, categories);
    log.info("Number of transactions created: {}", transactions.size());

    log.info("End of loading datas!");
  }

  private List<User> createUsers() {

    List<User> users = Arrays.asList(User.builder().firstName("Demo").lastName("Account")
        .login("demo").password(passwordEncoder.encode("demo")).build());

    return userRepository.saveAll(users);
  }

  private List<Account> createAccounts(List<User> users) {

    List<Account> accounts = users.stream()
        .flatMap(user -> Arrays
            .asList(Account.builder().name("Checking account").type("checking").user(user).build())
            .stream())
        .toList();

    return accountRepository.saveAll(accounts);

  }

  private List<Category> createCategories(List<User> users) {

    List<Category> categories =
        users.stream()
            .flatMap(user -> Arrays.asList(
                Category.builder().name("Alimentation").icon("ustensils").user(user).build(),
                Category.builder().name("Transport").icon("car").user(user).build(),
                Category.builder().name("Sorties").icon("beer-mug-empty").user(user).build(),
                Category.builder().name("Santé").icon("staff-snake").user(user).build(),
                Category.builder().name("Loisirs").icon("volleyball").user(user).build(),
                Category.builder().name("Autres").icon("tag").user(user).build()).stream())
            .toList();

    return categoryRepository.saveAll(categories);

  }

  private List<Transaction> createTransactions(List<Account> accounts, List<Category> categories) {

    List<Transaction> transactions =
        accounts.stream()
            .flatMap(
                account -> categories.stream()
                    .flatMap(
                        category -> Arrays
                            .asList(
                                Transaction.builder().name("Transaction").amount(12345)
                                    .account(account).category(category).checked(true)
                                    .regularization(false).type("debit").date("2025-03-25").build(),
                                Transaction.builder().name("Transaction").amount(123)
                                    .account(account).category(category).checked(true)
                                    .regularization(false).type("debit").date("2025-03-25").build(),
                                Transaction.builder().name("Transaction").amount(5678)
                                    .account(account).category(category).checked(true)
                                    .regularization(false).type("debit").date("2025-03-25").build(),
                                Transaction.builder().name("Transaction").amount(7896)
                                    .account(account).category(category).checked(true)
                                    .regularization(false).type("debit").date("2025-03-25").build(),
                                Transaction.builder().name("Transaction").amount(500)
                                    .account(account).category(category).checked(true)
                                    .regularization(false).type("debit").date("2025-03-25").build())
                            .stream()))
            .toList();

    return transactionRepository.saveAll(transactions);

  }

}
