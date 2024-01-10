package com.mateeusferro.backend.repositories;

import com.mateeusferro.backend.models.FinancialGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialGoalRepository extends JpaRepository<FinancialGoal, Long> {
}
