package com.mateeusferro.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "financial_goal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 45, message = "Name must be between 2 and 45 characters")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Value cannot be null")
    @Column(name = "value")
    private Double value;

    @NotNull(message = "Date cannot be null")
    @Column(name = "date")
    private Date date;

    @NotNull(message = "User ID cannot be null")
    @Column(name = "users_id")
    private Long usersId;

    @NotNull(message = "Currency ID cannot be null")
    @Column(name = "currency_id")
    private Long currencyId;

    @AssertTrue(message = "User ID must be greater than 0")
    private boolean isUserIdValid() {
        return usersId != null && usersId > 0;
    }

    @AssertTrue(message = "Currency ID must be greater than 0")
    private boolean isCurrencyIdValid() {
        return currencyId != null && currencyId > 0;
    }

    public FinancialGoal(String name, Double value, Date date, Long usersId, Long currencyId) {
        this.name = name;
        this.value = value;
        this.date = date;
        this.usersId = usersId;
        this.currencyId = currencyId;
    }
}
