package com.mateeusferro.backend.dtos;

import java.sql.Date;

public record ExpensesDTO(String type, Date date, Double value, Double paid, Date paidDate, String paymentType,
                          Long usersId, Long currencyId) {
}
