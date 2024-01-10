package com.mateeusferro.backend.repositories;

import com.mateeusferro.backend.models.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
}
