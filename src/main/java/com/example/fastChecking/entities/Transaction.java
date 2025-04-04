package com.example.fastChecking.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue
  @UuidGenerator
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer amount;

  @Column(nullable = false)
  private String date;

  @Column(nullable = false)
  private String type;

  @Column(nullable = false)
  private Boolean checked;

  @Column(nullable = false)
  private Boolean regularization;

  @ManyToOne
  @JoinColumn(name = "account_id", nullable = false)
  @JsonBackReference
  private Account account;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  @JsonBackReference
  private Category category;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  @JsonIgnore
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  @JsonIgnore
  private LocalDateTime updatedAt;

  @JsonGetter("accountId")
  public UUID accountId() {
    return account.getId();
  }

  @JsonGetter("categoryId")
  public UUID categoryId() {
    return category.getId();
  }
}
