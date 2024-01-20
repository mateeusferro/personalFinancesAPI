package com.mateeusferro.backend.dtos;

import java.time.LocalDate;

public record ExpensesDTO(String type, LocalDate date, Double value, Double paid, LocalDate paidDate, String paymentType,
                          String description, Long usersId, Long currencyId) {
}
