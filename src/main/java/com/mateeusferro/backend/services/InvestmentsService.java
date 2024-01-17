package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.InvestmentsDTO;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
import com.mateeusferro.backend.models.*;
import com.mateeusferro.backend.repositories.CurrencyRepository;
import com.mateeusferro.backend.repositories.InvestmentsRepository;
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
public class InvestmentsService {

    @Autowired
    InvestmentsRepository investmentsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public List<Investments> getInvestments(long id){
        Users user = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Investments account = investmentsRepository.findByUsersId(user);
        return Collections.singletonList(account);
    }

    public void createInvestment(InvestmentsDTO investmentsDTO){
        Users user = usersRepository.findById(investmentsDTO.usersId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Currency currency = currencyRepository.findById(investmentsDTO.currencyId()).orElseThrow(() -> new ResourceNotFoundException("Currency not found"));

        Investments investments = new Investments(investmentsDTO.type(), investmentsDTO.value(),
                                                investmentsDTO.date(), investmentsDTO.description(),
                user, currency);
        investmentsRepository.save(investments);
    }

    public void updateInvestment(long id, InvestmentsDTO investmentsDTO){
        Investments updateInvestment = investmentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment not exist with id: " + id));

        updateInvestment.setType(investmentsDTO.type());
        updateInvestment.setValue(investmentsDTO.value());
        updateInvestment.setDate(investmentsDTO.date());
        updateInvestment.setDescription(investmentsDTO.description());
        Currency currency = currencyRepository.findById(investmentsDTO.currencyId()).orElseThrow(() -> new ResourceNotFoundException("Currency not found"));

        updateInvestment.setCurrencyId(currency);

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
