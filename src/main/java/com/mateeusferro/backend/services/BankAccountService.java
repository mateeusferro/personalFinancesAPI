package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.BankAccountDTO;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
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
                bankAccountDTO.usersId(), bankAccountDTO.currencyId());

        bankAccountRepository.save(bankAccount);
    }

    public void updateBankAccount(long id, BankAccountDTO bankAccountDTO) {
        BankAccount updateBankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not exist with id: " + id));

        updateBankAccount.setName(bankAccountDTO.name());
        updateBankAccount.setBalance(bankAccountDTO.balance());
        updateBankAccount.setUsersId(bankAccountDTO.usersId());
        updateBankAccount.setCurrencyId(bankAccountDTO.currencyId());

        bankAccountRepository.save(updateBankAccount);
    }

}
