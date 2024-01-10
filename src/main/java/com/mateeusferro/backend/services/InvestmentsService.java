package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.InvestmentsDTO;
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
}
