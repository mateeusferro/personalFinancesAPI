package com.mateeusferro.backend.dtos;

import java.sql.Date;

public record FinancialGoalDTO(String name, Double value, Date date, Long usersId, Long currencyId) {
}
