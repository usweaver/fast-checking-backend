package com.example.fastChecking.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.fastChecking.constants.ExampleCategories;
import com.example.fastChecking.constants.ExampleCategory;
import com.example.fastChecking.constants.ExampleTransaction;
import com.example.fastChecking.constants.ExampleTransactions;
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
@Profile("prod")
@RequiredArgsConstructor
public class ProdDataLoader implements CommandLineRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AccountRepository accountRepository;
  private final CategoryRepository categoryRepository;
  private final TransactionRepository transactionRepository;

  @Override
  public void run(String... args) {
    log.info("Start loading datas...");

    deletePreviousData();

    List<User> users = createUsers();
    log.info("Number of users created: {}", users.size());
    List<Account> accounts = createAccounts(users);
    log.info("Number of accounts created: {}", accounts.size());
    List<Category> categories = createCategories(users);
    log.info("Number of categories created: {}", categories.size());
    List<Transaction> transactions =
        createTransactions(accounts, categories, getPreviousMonth(0), selectExampleTransactions());
    List<Transaction> transactions2 = createTransactions(accounts, categories, getPreviousMonth(1),
        ExampleTransactions.EXEMPLE_TRANSACTIONS.getTransactions());
    List<Transaction> transactions3 = createTransactions(accounts, categories, getPreviousMonth(2),
        ExampleTransactions.EXEMPLE_TRANSACTIONS.getTransactions());
    log.info("Number of transactions created: {}",
        transactions.size() + transactions2.size() + transactions3.size());

    log.info("End of loading datas!");
  }

  private void deletePreviousData() {
    User demoUser = userRepository.findByLogin("demo").orElse(null);
    if (demoUser != null) {
      userRepository.delete(demoUser);
    }
  }

  private List<User> createUsers() {

    List<User> users = Arrays.asList(User.builder().firstName("Demo").lastName("Account")
        .login("demo").password(passwordEncoder.encode("demo")).build());

    return userRepository.saveAll(users);
  }

  private List<Account> createAccounts(List<User> users) {
    List<Account> accounts = new ArrayList<>();
    for (User user : users) {
      accounts.add(Account.builder().name("Checking account").type("checking").user(user)
          .balance(50000).build());
    }

    return accountRepository.saveAll(accounts);
  }

  private List<Category> createCategories(List<User> users) {
    List<Category> categories = new ArrayList<>();

    for (User user : users) {
      for (ExampleCategory exampleCategory : ExampleCategories.CATEGORIES.getCategories()) {
        categories.add(Category.builder().name(exampleCategory.getName())
            .icon(exampleCategory.getIcon()).user(user).build());
      }
    }

    return categoryRepository.saveAll(categories);

  }

  private List<Transaction> createTransactions(List<Account> accounts, List<Category> categories,
      String month, ExampleTransaction[] exampleTransactions) {
    List<Transaction> transactions = new ArrayList<>();

    for (Account account : accounts) {
      for (ExampleTransaction exampleTransaction : exampleTransactions) {
        transactions.add(Transaction.builder().name(exampleTransaction.getName())
            .amount(exampleTransaction.getAmount()).account(account)
            .category(categories.get(exampleTransaction.getCategoryId())).checked(true)
            .regularization(false).type(exampleTransaction.getType())
            .date(month + stringifyDay(exampleTransaction.getDay())).build());
      }
    }

    return transactionRepository.saveAll(transactions);
  }

  private String getPreviousMonth(Integer delta) {
    LocalDate previousDate = java.time.LocalDate.now().minusMonths(delta);

    int year = previousDate.getYear();
    int monthValue = previousDate.getMonthValue();
    String month = monthValue < 10 ? "0" + monthValue : String.valueOf(monthValue);
    return year + "-" + month;
  }

  private ExampleTransaction[] selectExampleTransactions() {
    Integer day = java.time.LocalDate.now().getDayOfMonth();
    return Arrays.stream(ExampleTransactions.EXEMPLE_TRANSACTIONS.getTransactions())
        .filter(exampleTransaction -> exampleTransaction.getDay() <= day)
        .toArray(ExampleTransaction[]::new);
  }

  private String stringifyDay(Integer day) {
    return day < 10 ? "-0" + day : "-" + day;
  }

}
