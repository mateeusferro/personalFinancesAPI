package com.mateeusferro.backend.dtos;

import java.sql.Date;

public record SalaryDTO(String type, Double value, Date date, String description, Long usersId, Long currencyId) {
}
