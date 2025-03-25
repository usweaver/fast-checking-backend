package com.example.fastChecking.dto;

import java.util.List;
import com.example.fastChecking.entities.Account;
import com.example.fastChecking.entities.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfosDto {
  private String firstName;
  private String lastName;
  private List<Account> accounts;
  private List<Category> categories;

}
