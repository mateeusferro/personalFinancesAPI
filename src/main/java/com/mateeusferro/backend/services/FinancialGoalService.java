package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.FinancialGoalDTO;
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
}
