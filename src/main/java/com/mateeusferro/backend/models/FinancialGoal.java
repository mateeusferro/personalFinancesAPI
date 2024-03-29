package com.mateeusferro.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate date;

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

    public FinancialGoal(String name, Double value, LocalDate date, Users usersId, Currency currencyId) {
        this.name = name;
        this.value = value;
        this.date = date;
        this.usersId = usersId;
        this.currencyId = currencyId;
    }
}
