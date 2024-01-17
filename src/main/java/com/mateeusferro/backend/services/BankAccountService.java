package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.BankAccountDTO;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
import com.mateeusferro.backend.models.BankAccount;
import com.mateeusferro.backend.models.Currency;
import com.mateeusferro.backend.models.Users;
import com.mateeusferro.backend.repositories.BankAccountRepository;
import com.mateeusferro.backend.repositories.CurrencyRepository;
import com.mateeusferro.backend.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public List<BankAccount> getBankAccount(long id){
        Users user = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        BankAccount account = bankAccountRepository.findByUsersId(user);
        return Collections.singletonList(account);
    }


    public void createBankAccount(BankAccountDTO bankAccountDTO){
        Users user = usersRepository.findById(bankAccountDTO.usersId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Currency currency = currencyRepository.findById(bankAccountDTO.currencyId()).orElseThrow(() -> new ResourceNotFoundException("Currency not found"));

        BankAccount bankAccount = new BankAccount(bankAccountDTO.name(), bankAccountDTO.balance(),
                user, currency);

        bankAccountRepository.save(bankAccount);
    }

    public void updateBankAccount(long id, BankAccountDTO bankAccountDTO) {
        BankAccount updateBankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not exist with id: " + id));

        updateBankAccount.setName(bankAccountDTO.name());
        updateBankAccount.setBalance(bankAccountDTO.balance());
        Currency currency = currencyRepository.findById(bankAccountDTO.currencyId()).orElseThrow(() -> new ResourceNotFoundException("Currency not found"));

        updateBankAccount.setCurrencyId(currency);

        bankAccountRepository.save(updateBankAccount);
    }

    @Transactional
    public void partialUpdateBankAccount(long id, Map<String, Object> updates) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);
        if (bankAccount.isPresent()) {
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(BankAccount.class, (String) key);

                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, bankAccount, value);
                } else {
                    throw new IllegalArgumentException("Field not found: " + key);
                }

            });
            bankAccountRepository.save(bankAccount.get());
        }
    }

    public void deleteBankAccount(long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Bank account not found"));
        bankAccountRepository.delete(bankAccount);
    }

}
