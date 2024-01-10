package com.mateeusferro.backend.repositories;

import com.mateeusferro.backend.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long > {
}
