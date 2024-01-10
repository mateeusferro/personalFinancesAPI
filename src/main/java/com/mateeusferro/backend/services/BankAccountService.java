package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.BankAccountDTO;
import com.mateeusferro.backend.models.BankAccount;
import com.mateeusferro.backend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    public void createBankAccount(BankAccountDTO bankAccountDTO){
        BankAccount bankAccount = new BankAccount(bankAccountDTO.name(), bankAccountDTO.balance(),
                bankAccountDTO.userId(), bankAccountDTO.currencyId());

        bankAccountRepository.save(bankAccount);
    }
}
