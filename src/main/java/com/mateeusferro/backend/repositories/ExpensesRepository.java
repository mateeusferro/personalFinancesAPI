package com.mateeusferro.backend.repositories;

import com.mateeusferro.backend.models.BankAccount;
import com.mateeusferro.backend.models.Expenses;
import com.mateeusferro.backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
    Expenses findByUsersId(Users users);

}
