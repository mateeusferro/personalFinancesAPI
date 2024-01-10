package com.mateeusferro.backend.dtos;

public record BankAccountDTO(String name, Double balance, Long userId, Long currencyId) {
}
