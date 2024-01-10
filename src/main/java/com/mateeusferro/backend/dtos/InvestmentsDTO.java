package com.mateeusferro.backend.dtos;

import java.sql.Date;

public record InvestmentsDTO(String type, Double value, Date date, String description,
                             Long usersId, Long currencyId) {
}
