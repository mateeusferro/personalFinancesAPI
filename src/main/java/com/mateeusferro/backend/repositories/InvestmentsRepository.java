package com.mateeusferro.backend.repositories;

import com.mateeusferro.backend.models.Investments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentsRepository extends JpaRepository<Investments, Long> {
}
