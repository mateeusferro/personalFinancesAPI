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
@Table(name = "expenses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Type cannot be blank")
    @Size(min = 2, max = 45, message = "Type must be between 2 and 45 characters")
    @Column(name = "type")
    private String type;

    @NotNull(message = "Date cannot be null")
    @Column(name = "date")
    private LocalDate date;

    @NotNull(message = "Value cannot be null")
    @Column(name = "value")
    private Double value;

    @Size(max = 200, message = "Description must be max of 200 characters")
    @Column(name = "description")
    private String description;

    @Column(name = "paid")
    private Double paid;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    @NotBlank(message = "Payment type cannot be null")
    @Size(min = 2, max = 90, message = "Payment type must be between 2 and 45 characters")
    @Column(name = "payment_type")
    private String paymentType;

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

    public Expenses(String type, LocalDate date, String description, Double value, Double paid, LocalDate paidDate, String paymentType,
                    Users usersId, Currency currencyId) {
        this.type = type;
        this.date = date;
        this.value = value;
        this.description = description;
        this.paid = paid;
        this.paidDate = paidDate;
        this.paymentType = paymentType;
        this.usersId = usersId;
        this.currencyId = currencyId;
    }
}
