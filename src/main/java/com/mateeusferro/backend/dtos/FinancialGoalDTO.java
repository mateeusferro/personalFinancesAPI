package com.mateeusferro.backend.dtos;

import java.time.LocalDate;

public record FinancialGoalDTO(String name, Double value, LocalDate date, Long usersId, Long currencyId) {
}
