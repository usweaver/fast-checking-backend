package com.example.fastChecking.dto.dashboard;

import java.util.List;
import com.example.fastChecking.entities.Transaction;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardAccountInfosResponseDto {
  private Integer balance;
  private List<Transaction> recentTransactions;
}
