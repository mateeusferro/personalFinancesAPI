package com.mateeusferro.backend.repositories;

import com.mateeusferro.backend.models.Salary;
import com.mateeusferro.backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Salary findByUsersId(Users users);

}
