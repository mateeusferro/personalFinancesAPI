package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.ExpensesDTO;
import com.mateeusferro.backend.dtos.FinancialGoalDTO;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
import com.mateeusferro.backend.models.Expenses;
import com.mateeusferro.backend.models.FinancialGoal;
import com.mateeusferro.backend.repositories.FinancialGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialGoalService {

    @Autowired
    FinancialGoalRepository financialGoalRepository;
    public void createFinancialGoal(FinancialGoalDTO financialGoalDTO){
        FinancialGoal financialGoal = new FinancialGoal(financialGoalDTO.name(), financialGoalDTO.value(),
                                                        financialGoalDTO.date(), financialGoalDTO.usersId(),
                                                        financialGoalDTO.currencyId());
        financialGoalRepository.save(financialGoal);
    }

    public void updateFinancialGoal(long id, FinancialGoalDTO financialGoalDTO){
        FinancialGoal updateFinancialGoal = financialGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Financial goal not exist with id: " + id));

        updateFinancialGoal.setName(financialGoalDTO.name());
        updateFinancialGoal.setValue(financialGoalDTO.value());
        updateFinancialGoal.setDate(financialGoalDTO.date());
        updateFinancialGoal.setUsersId(financialGoalDTO.usersId());
        updateFinancialGoal.setCurrencyId(financialGoalDTO.currencyId());

        financialGoalRepository.save(updateFinancialGoal);
    }
}
