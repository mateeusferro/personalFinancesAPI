package com.mateeusferro.backend.repositories;

import com.mateeusferro.backend.models.Investments;
import com.mateeusferro.backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentsRepository extends JpaRepository<Investments, Long> {
    Investments findByUsersId(Users users);
}
