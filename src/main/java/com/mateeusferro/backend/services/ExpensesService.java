package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.ExpensesDTO;
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
}
