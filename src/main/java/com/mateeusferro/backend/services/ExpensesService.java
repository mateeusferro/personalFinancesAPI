package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.ExpensesDTO;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
import com.mateeusferro.backend.models.Expenses;
import com.mateeusferro.backend.models.Users;
import com.mateeusferro.backend.repositories.ExpensesRepository;
import com.mateeusferro.backend.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpensesService {

    @Autowired
    ExpensesRepository expensesRepository;

    @Autowired
    UsersRepository usersRepository;

    public void createExpense(ExpensesDTO expensesDTO){
        Users user = usersRepository.findById(expensesDTO.usersId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Expenses expenses = new Expenses(expensesDTO.type(), expensesDTO.date(), expensesDTO.value(),
                                        expensesDTO.paid(), expensesDTO.paidDate(), expensesDTO.paymentType(),
                user, expensesDTO.currencyId());
        expensesRepository.save((expenses));
    }

    public void updateExpense(long id, ExpensesDTO expensesDTO){
        Expenses updateExpenses = expensesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not exist with id: " + id));

        updateExpenses.setType(expensesDTO.type());
        updateExpenses.setDate(expensesDTO.date());
        updateExpenses.setValue(expensesDTO.value());
        updateExpenses.setPaid(expensesDTO.paid());
        updateExpenses.setPaidDate(expensesDTO.paidDate());
        updateExpenses.setPaymentType(expensesDTO.paymentType());
        updateExpenses.setCurrencyId(expensesDTO.currencyId());

        expensesRepository.save(updateExpenses);
    }

    @Transactional
    public void partialUpdateExpense(long id, Map<String, Object> updates) {
        Optional<Expenses> expenses = expensesRepository.findById(id);
        if (expenses.isPresent()) {
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Expenses.class, (String) key);

                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, expenses, value);
                } else {
                    throw new IllegalArgumentException("Field not found: " + key);
                }

            });
            expensesRepository.save(expenses.get());
        }
    }

    public void deleteExpense(long id) {
        Expenses expenses = expensesRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Expense not found"));
        expensesRepository.delete(expenses);
    }
}
