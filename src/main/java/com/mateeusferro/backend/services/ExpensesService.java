package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.ExpensesDTO;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
import com.mateeusferro.backend.models.BankAccount;
import com.mateeusferro.backend.models.Currency;
import com.mateeusferro.backend.models.Expenses;
import com.mateeusferro.backend.models.Users;
import com.mateeusferro.backend.repositories.CurrencyRepository;
import com.mateeusferro.backend.repositories.ExpensesRepository;
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
public class ExpensesService {

    @Autowired
    ExpensesRepository expensesRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public List<Expenses> getExpenses(long id){
        Users user = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Expenses expenses = expensesRepository.findByUsersId(user);
        return Collections.singletonList(expenses);
    }

    public void createExpense(ExpensesDTO expensesDTO){
        Users user = usersRepository.findById(expensesDTO.usersId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Currency currency = currencyRepository.findById(expensesDTO.currencyId()).orElseThrow(() -> new ResourceNotFoundException("Currency not found"));

        Expenses expenses = new Expenses(expensesDTO.type(), expensesDTO.date(), expensesDTO.description(), expensesDTO.value(),
                                        expensesDTO.paid(), expensesDTO.paidDate(), expensesDTO.paymentType(),
                user, currency);
        expensesRepository.save((expenses));
    }

    public void updateExpense(long id, ExpensesDTO expensesDTO){
        Expenses updateExpenses = expensesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not exist with id: " + id));

        updateExpenses.setType(expensesDTO.type());
        updateExpenses.setDate(expensesDTO.date());
        updateExpenses.setValue(expensesDTO.value());
        updateExpenses.setPaid(expensesDTO.paid());
        updateExpenses.setDescription(expensesDTO.description());
        updateExpenses.setPaidDate(expensesDTO.paidDate());
        updateExpenses.setPaymentType(expensesDTO.paymentType());
        Currency currency = currencyRepository.findById(expensesDTO.currencyId()).orElseThrow(() -> new ResourceNotFoundException("Currency not found"));

        updateExpenses.setCurrencyId(currency);

        expensesRepository.save(updateExpenses);
    }

    @Transactional
    public void partialUpdateExpense(long id, Map<String, Object> updates) {
        Optional<Expenses> optionalExpenses = expensesRepository.findById(id);
        if (optionalExpenses.isPresent()) {
            Expenses expenses = optionalExpenses.get();
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Expenses.class, key);

                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, expenses, value);
                } else {
                    throw new IllegalArgumentException("Field not found: " + key);
                }
            });
            expensesRepository.save(expenses);
        }
    }


    public void deleteExpense(long id) {
        Expenses expenses = expensesRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Expense not found"));
        expensesRepository.delete(expenses);
    }
}
