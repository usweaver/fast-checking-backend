package com.example.fastChecking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequestDto {
  private String name;
  private Integer amount;
  private String date;
  private String type;
  private Boolean checked;
  private Boolean regularization;
  private Long categoryId;
  private Long accountId;
}
