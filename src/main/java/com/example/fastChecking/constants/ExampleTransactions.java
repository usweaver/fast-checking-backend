package com.example.fastChecking.constants;

import lombok.Getter;

public enum ExampleTransactions {
  EXEMPLE_TRANSACTIONS(new ExampleTransaction[] {
      ExampleTransaction.builder().name("Drive").amount(7805).day(25).type("debit").categoryId(0)
          .build(),
      ExampleTransaction.builder().name("Doliprane").amount(520).day(16).type("debit").categoryId(5)
          .build(),
      ExampleTransaction.builder().name("Ninkasi").amount(1500).day(15).type("debit").categoryId(4)
          .build(),
      ExampleTransaction.builder().name("Abonnement Basic-fit").amount(2999).day(12).type("debit")
          .categoryId(6).build(),
      ExampleTransaction.builder().name("Drive").amount(6955).day(10).type("debit").categoryId(0)
          .build(),
      ExampleTransaction.builder().name("Assurance Auto").amount(7000).day(8).type("debit")
          .categoryId(12).build(),
      ExampleTransaction.builder().name("Assurance Habitation").amount(999).day(8).type("debit")
          .categoryId(12).build(),
      ExampleTransaction.builder().name("Box internet").amount(2400).day(7).type("debit")
          .categoryId(3).build(),
      ExampleTransaction.builder().name("Forfait mobile").amount(1999).day(5).type("debit")
          .categoryId(3).build(),
      ExampleTransaction.builder().name("Loyer").amount(58500).day(5).type("debit").categoryId(2)
          .build(),
      ExampleTransaction.builder().name("Salaire").amount(190000).day(3).type("credit")
          .categoryId(10).build(),});

  @Getter
  private final ExampleTransaction[] transactions;

  ExampleTransactions(ExampleTransaction[] transactions) {
    this.transactions = transactions;
  }

}
