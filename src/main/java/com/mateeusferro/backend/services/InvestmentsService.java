package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.InvestmentsDTO;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
import com.mateeusferro.backend.models.Investments;
import com.mateeusferro.backend.models.Salary;
import com.mateeusferro.backend.models.Users;
import com.mateeusferro.backend.repositories.InvestmentsRepository;
import com.mateeusferro.backend.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class InvestmentsService {

    @Autowired
    InvestmentsRepository investmentsRepository;

    @Autowired
    UsersRepository usersRepository;

    public void createInvestment(InvestmentsDTO investmentsDTO){
        Users user = usersRepository.findById(investmentsDTO.usersId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Investments investments = new Investments(investmentsDTO.type(), investmentsDTO.value(),
                                                investmentsDTO.date(), investmentsDTO.description(),
                user, investmentsDTO.currencyId());
        investmentsRepository.save(investments);
    }

    public void updateInvestment(long id, InvestmentsDTO investmentsDTO){
        Investments updateInvestment = investmentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment not exist with id: " + id));

        updateInvestment.setType(investmentsDTO.type());
        updateInvestment.setValue(investmentsDTO.value());
        updateInvestment.setDate(investmentsDTO.date());
        updateInvestment.setDescription(investmentsDTO.description());
        updateInvestment.setCurrencyId(investmentsDTO.currencyId());

        investmentsRepository.save(updateInvestment);
    }

    @Transactional
    public void partialUpdateInvestments(long id, Map<String, Object> updates) {
        Optional<Investments> investments = investmentsRepository.findById(id);
        if (investments.isPresent()) {
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Investments.class, (String) key);

                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, investments, value);
                } else {
                    throw new IllegalArgumentException("Field not found: " + key);
                }

            });
            investmentsRepository.save(investments.get());
        }
    }

    public void deleteInvestments(long id) {
        Investments investments = investmentsRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Investment not found"));
        investmentsRepository.delete(investments);
    }
}
