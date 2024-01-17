package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.FinancialGoalDTO;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
import com.mateeusferro.backend.models.FinancialGoal;
import com.mateeusferro.backend.models.Investments;
import com.mateeusferro.backend.models.Users;
import com.mateeusferro.backend.repositories.FinancialGoalRepository;
import com.mateeusferro.backend.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class FinancialGoalService {

    @Autowired
    FinancialGoalRepository financialGoalRepository;

    @Autowired
    UsersRepository usersRepository;

    public void createFinancialGoal(FinancialGoalDTO financialGoalDTO){
        Users user = usersRepository.findById(financialGoalDTO.usersId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        FinancialGoal financialGoal = new FinancialGoal(financialGoalDTO.name(), financialGoalDTO.value(),
                                                        financialGoalDTO.date(), user,
                                                        financialGoalDTO.currencyId());
        financialGoalRepository.save(financialGoal);
    }

    public void updateFinancialGoal(long id, FinancialGoalDTO financialGoalDTO){
        FinancialGoal updateFinancialGoal = financialGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Financial goal not exist with id: " + id));

        updateFinancialGoal.setName(financialGoalDTO.name());
        updateFinancialGoal.setValue(financialGoalDTO.value());
        updateFinancialGoal.setDate(financialGoalDTO.date());
        updateFinancialGoal.setCurrencyId(financialGoalDTO.currencyId());

        financialGoalRepository.save(updateFinancialGoal);
    }

    @Transactional
    public void partialUpdateFinancialGoal(long id, Map<String, Object> updates) {
        Optional<FinancialGoal> financialGoal = financialGoalRepository.findById(id);
        if (financialGoal.isPresent()) {
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(FinancialGoal.class, (String) key);

                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, financialGoal, value);
                } else {
                    throw new IllegalArgumentException("Field not found: " + key);
                }

            });
            financialGoalRepository.save(financialGoal.get());
        }
    }

    public void deleteFinancialGoal(long id) {
        FinancialGoal financialGoal = financialGoalRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Financial goal not found"));
        financialGoalRepository.delete(financialGoal);
    }}
