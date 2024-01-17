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
@Table(name = "investments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Investments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Type cannot be blank")
    @Size(min = 2, max = 45, message = "Type must be between 2 and 45 characters")
    @Column(name = "type")
    private String type;

    @NotNull(message = "Value cannot be null")
    @Column(name = "value")
    private Double value;

    @NotNull(message = "Date cannot be null")
    @Column(name = "date")
    private Date date;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 2, max = 200, message = "Description must be between 2 and 200 characters")
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private Users usersId;

    @NotNull(message = "Currency ID cannot be null")
    @Column(name = "currency_id")
    private Long currencyId;

    @AssertTrue(message = "User ID must be greater than 0")
    private boolean isUserIdValid() {
        return usersId != null && usersId.getId() > 0;
    }

    @AssertTrue(message = "Currency ID must be greater than 0")
    private boolean isCurrencyIdValid() {
        return currencyId != null && currencyId > 0;
    }

    public Investments(String type, Double value, Date date, String description,
                       Users usersId, Long currencyId) {
        this.type = type;
        this.value = value;
        this.date = date;
        this.description = description;
        this.usersId = usersId;
        this.currencyId = currencyId;
    }
}
