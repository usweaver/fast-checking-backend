package com.example.fastChecking.dto.transactions;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddTransactionRequestDto {
  private String name;
  private Integer amount;
  private String date;
  private String type;
  private Boolean checked;
  private Boolean regularization;
  private UUID categoryId;
  private UUID accountId;
}
