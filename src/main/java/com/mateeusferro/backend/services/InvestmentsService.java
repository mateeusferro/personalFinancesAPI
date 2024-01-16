package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.FinancialGoalDTO;
import com.mateeusferro.backend.dtos.InvestmentsDTO;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
import com.mateeusferro.backend.models.FinancialGoal;
import com.mateeusferro.backend.models.Investments;
import com.mateeusferro.backend.repositories.InvestmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestmentsService {

    @Autowired
    InvestmentsRepository investmentsRepository;

    public void createInvestment(InvestmentsDTO investmentsDTO){
        Investments investments = new Investments(investmentsDTO.type(), investmentsDTO.value(),
                                                investmentsDTO.date(), investmentsDTO.description(),
                                                investmentsDTO.usersId(), investmentsDTO.currencyId());
        investmentsRepository.save(investments);
    }

    public void updateInvestment(long id, InvestmentsDTO investmentsDTO){
        Investments updateInvestment = investmentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment not exist with id: " + id));

        updateInvestment.setType(investmentsDTO.type());
        updateInvestment.setValue(investmentsDTO.value());
        updateInvestment.setDate(investmentsDTO.date());
        updateInvestment.setDescription(investmentsDTO.description());
        updateInvestment.setUsersId(investmentsDTO.usersId());
        updateInvestment.setCurrencyId(investmentsDTO.currencyId());

        investmentsRepository.save(updateInvestment);
    }
}
