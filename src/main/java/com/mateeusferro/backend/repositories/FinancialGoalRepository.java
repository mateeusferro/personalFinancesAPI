package com.mateeusferro.backend.repositories;

import com.mateeusferro.backend.models.FinancialGoal;
import com.mateeusferro.backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialGoalRepository extends JpaRepository<FinancialGoal, Long> {
    FinancialGoal findByUsersId(Users users);

}
