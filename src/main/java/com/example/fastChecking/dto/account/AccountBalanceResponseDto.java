package com.example.fastChecking.dto.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountBalanceResponseDto {
  private Integer balance;
}
