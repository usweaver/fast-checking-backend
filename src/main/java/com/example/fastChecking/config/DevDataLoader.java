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
public class DevDataLoader implements CommandLineRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AccountRepository accountRepository;
  private final CategoryRepository categoryRepository;
  private final TransactionRepository transactionRepository;

  @Override
  public void run(String... args) {
    log.info("Start loading datas...");

    userRepository.deleteAll();

    List<User> users = createUsers();
    log.info("Number of users created: {}", users.size());
    List<Account> accounts = createAccounts(users);
    log.info("Number of accounts created: {}", accounts.size());
    List<Category> categories = createCategories(users);
    log.info("Number of categories created: {}", categories.size());
    List<Transaction> transactions = createTransactions(accounts, categories, "2025-03");
    List<Transaction> transactions2 = createTransactions(accounts, categories, "2025-02");
    List<Transaction> transactions3 = createTransactions(accounts, categories, "2025-01");
    log.info("Number of transactions created: {}",
        transactions.size() + transactions2.size() + transactions3.size());

    log.info("End of loading datas!");
  }

  private List<User> createUsers() {

    List<User> users = Arrays.asList(User.builder().firstName("Demo").lastName("Account")
        .login("demo").password(passwordEncoder.encode("demo")).build());

    return userRepository.saveAll(users);
  }

  private List<Account> createAccounts(List<User> users) {

    List<Account> accounts =
        users.stream().flatMap(user -> Arrays.asList(Account.builder().name("Checking account")
            .type("checking").user(user).balance(50000).build()).stream()).toList();

    return accountRepository.saveAll(accounts);

  }

  private List<Category> createCategories(List<User> users) {

    List<Category> categories = users.stream()
        .flatMap(user -> Arrays
            .asList(Category.builder().name("Alimentation").icon("utensils").user(user).build(),
                Category.builder().name("Transport").icon("car").user(user).build(),
                Category.builder().name("Logement").icon("house").user(user).build(),
                Category.builder().name("Télécommunications").icon("wifi").user(user).build(),
                Category.builder().name("Sorties").icon("beer-mug-empty").user(user).build(),
                Category.builder().name("Santé").icon("staff-snake").user(user).build(),
                Category.builder().name("Loisirs").icon("volleyball").user(user).build(),
                Category.builder().name("Voyages").icon("plane-up").user(user).build(),
                Category.builder().name("Vêtements").icon("shirt").user(user).build(),
                Category.builder().name("Épargne").icon("building-columns").user(user).build(),
                Category.builder().name("Salaires").icon("money-check-dollar").user(user).build(),
                Category.builder().name("Impôts").icon("flag-usa").user(user).build(),
                Category.builder().name("Assurances").icon("shield-halved").user(user).build(),
                Category.builder().name("Autres").icon("tag").user(user).build(), Category.builder()
                    .name("Régularisation").icon("arrow-right-arrow-left").user(user).build())
            .stream())
        .toList();

    return categoryRepository.saveAll(categories);

  }

  private List<Transaction> createTransactions(List<Account> accounts, List<Category> categories,
      String month) {

    List<Transaction> transactions = accounts.stream()
        .flatMap(account -> Arrays.asList(
            Transaction.builder().name("Drive").amount(7805).account(account)
                .category(categories.get(0)).checked(true).regularization(false).type("debit")
                .date(month + "-25").build(),
            Transaction.builder().name("Doliprane").amount(520).account(account)
                .category(categories.get(5)).checked(true).regularization(false).type("debit")
                .date(month + "-16").build(),
            Transaction.builder().name("Ninkasi").amount(1500).account(account)
                .category(categories.get(4)).checked(true).regularization(false).type("debit")
                .date(month + "-15").build(),
            Transaction.builder().name("Abonnement Basic-fit").amount(2999).account(account)
                .category(categories.get(6)).checked(true).regularization(false).type("debit")
                .date(month + "-12").build(),
            Transaction.builder().name("Drive").amount(6955).account(account)
                .category(categories.get(0)).checked(true).regularization(false).type("debit")
                .date(month + "-10").build(),
            Transaction.builder().name("Assurance auto").amount(7000).account(account)
                .category(categories.get(12)).checked(true).regularization(false).type("debit")
                .date(month + "-08").build(),
            Transaction.builder().name("Assurance habitation").amount(999).account(account)
                .category(categories.get(12)).checked(true).regularization(false).type("debit")
                .date(month + "-08").build(),
            Transaction.builder().name("Box internet").amount(2400).account(account)
                .category(categories.get(3)).checked(true).regularization(false).type("debit")
                .date(month + "-07").build(),
            Transaction.builder().name("Forfait mobile").amount(1999).account(account)
                .category(categories.get(3)).checked(true).regularization(false).type("debit")
                .date(month + "-05").build(),
            Transaction.builder().name("Loyer").amount(58500).account(account)
                .category(categories.get(2)).checked(true).regularization(false).type("debit")
                .date(month + "-05").build(),
            Transaction.builder().name("Salaire").amount(190000).account(account)
                .category(categories.get(10)).checked(true).regularization(false).type("credit")
                .date(month + "-03").build())
            .stream())
        .toList();

    return transactionRepository.saveAll(transactions);

  }

}
