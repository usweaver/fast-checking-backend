package com.example.fastChecking.constants;

import lombok.Getter;

public enum ExampleCategories {
  CATEGORIES(new ExampleCategory[] {
      ExampleCategory.builder().name("Alimentation").icon("utensils").build(),
      ExampleCategory.builder().name("Transport").icon("car").build(),
      ExampleCategory.builder().name("Logement").icon("house").build(),
      ExampleCategory.builder().name("Télécommunications").icon("wifi").build(),
      ExampleCategory.builder().name("Sorties").icon("beer-mug-empty").build(),
      ExampleCategory.builder().name("Santé").icon("staff-snake").build(),
      ExampleCategory.builder().name("Loisirs").icon("volleyball").build(),
      ExampleCategory.builder().name("Voyages").icon("plane-up").build(),
      ExampleCategory.builder().name("Vêtements").icon("shirt").build(),
      ExampleCategory.builder().name("Épargne").icon("building-columns").build(),
      ExampleCategory.builder().name("Salaires").icon("money-check-dollar").build(),
      ExampleCategory.builder().name("Impôts").icon("flag-usa").build(),
      ExampleCategory.builder().name("Assurances").icon("shield-halved").build(),
      ExampleCategory.builder().name("Autres").icon("tag").build(),
      ExampleCategory.builder().name("Régularisation").icon("arrow-right-arrow-left").build(),});

  @Getter
  private final ExampleCategory[] categories;

  ExampleCategories(ExampleCategory[] categories) {
    this.categories = categories;
  }
}
