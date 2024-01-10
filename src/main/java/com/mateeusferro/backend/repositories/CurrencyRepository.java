package com.mateeusferro.backend.repositories;

import com.mateeusferro.backend.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
