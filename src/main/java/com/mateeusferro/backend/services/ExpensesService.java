package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.ExpensesDTO;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
import com.mateeusferro.backend.models.BankAccount;
import com.mateeusferro.backend.models.Expenses;
import com.mateeusferro.backend.repositories.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpensesService {

    @Autowired
    ExpensesRepository expensesRepository;
    public void createExpense(ExpensesDTO expensesDTO){
        Expenses expenses = new Expenses(expensesDTO.type(), expensesDTO.date(), expensesDTO.value(),
                                        expensesDTO.paid(), expensesDTO.paidDate(), expensesDTO.paymentType(),
                                        expensesDTO.usersId(), expensesDTO.currencyId());
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
        updateExpenses.setUsersId(expensesDTO.usersId());
        updateExpenses.setCurrencyId(expensesDTO.currencyId());

        expensesRepository.save(updateExpenses);
    }
}
