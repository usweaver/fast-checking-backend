package com.example.fastChecking.dto.account;

import java.util.List;
import com.example.fastChecking.entities.Transaction;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountInfosResponseDto {
  private Integer balance;
  private List<String> months;
  private List<Transaction> transactions;
}
