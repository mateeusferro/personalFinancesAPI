package com.mateeusferro.backend.dtos;

import java.time.LocalDate;

public record InvestmentsDTO(String type, Double value, LocalDate date, String description,
                             Long usersId, Long currencyId) {
}
