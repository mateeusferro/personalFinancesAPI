package com.mateeusferro.backend.repositories;

import com.mateeusferro.backend.models.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
}
