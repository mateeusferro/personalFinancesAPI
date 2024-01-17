package com.mateeusferro.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bank_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 45, message = "Name must be between 2 and 45 characters")
    private String name;

    @Column(name = "balance")
    @NotNull(message = "Balance cannot be null")
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private Users usersId;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currencyId;

    @AssertTrue(message = "User ID must be greater than 0")
    private boolean isUserIdValid() {
        return usersId != null && usersId.getId() > 0;
    }

    @AssertTrue(message = "Currency ID must be greater than 0")
    private boolean isCurrencyIdValid() {
        return currencyId != null && currencyId.getId() > 0;
    }

    public BankAccount(String name, Double balance, Users usersId, Currency currencyId) {
        this.name = name;
        this.balance = balance;
        this.usersId = usersId;
        this.currencyId = currencyId;
    }
}
