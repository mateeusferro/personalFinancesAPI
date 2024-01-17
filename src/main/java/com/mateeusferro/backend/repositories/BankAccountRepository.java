package com.mateeusferro.backend.repositories;

import com.mateeusferro.backend.models.BankAccount;
import com.mateeusferro.backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long > {
    BankAccount findByUsersId(Users users);
}
