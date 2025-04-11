package com.example.fastChecking.constants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ExampleTransaction {

  private String name;
  private Integer amount;
  private Integer day;
  private String type;
  private Integer categoryId;

}
