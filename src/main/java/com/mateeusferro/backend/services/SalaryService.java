package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.SalaryDTO;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
import com.mateeusferro.backend.models.BankAccount;
import com.mateeusferro.backend.models.Currency;
import com.mateeusferro.backend.models.Salary;
import com.mateeusferro.backend.models.Users;
import com.mateeusferro.backend.repositories.CurrencyRepository;
import com.mateeusferro.backend.repositories.SalaryRepository;
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
public class SalaryService {

    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public List<Salary> getSalary(long id){
        Users user = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Salary salary = salaryRepository.findByUsersId(user);
        return Collections.singletonList(salary);
    }

    public void createSalary(SalaryDTO salaryDTO){
        Users user = usersRepository.findById(salaryDTO.usersId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Currency currency = currencyRepository.findById(salaryDTO.currencyId()).orElseThrow(() -> new ResourceNotFoundException("Currency not found"));
        Salary salary = new Salary(salaryDTO.type(), salaryDTO.value(), salaryDTO.date(),
                                    salaryDTO.description(), user, currency);
        salaryRepository.save(salary);
    }

    public void updateSalary(long id, SalaryDTO salaryDTO){
        Salary updateSalary = salaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salary not exist with id: " + id));

        updateSalary.setType(salaryDTO.type());
        updateSalary.setValue(salaryDTO.value());
        updateSalary.setDate(salaryDTO.date());
        updateSalary.setDescription(salaryDTO.description());
        Currency currency = currencyRepository.findById(salaryDTO.currencyId()).orElseThrow(() -> new ResourceNotFoundException("Currency not found"));

        updateSalary.setCurrencyId(currency);

        salaryRepository.save(updateSalary);
    }

    @Transactional
    public void partialUpdateSalary(long id, Map<String, Object> updates) {
        Optional<Salary> optionalSalary = salaryRepository.findById(id);
        if (optionalSalary.isPresent()) {
            Salary salary = optionalSalary.get();
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Salary.class, key);

                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, salary, value);
                } else {
                    throw new IllegalArgumentException("Field not found: " + key);
                }
            });
            salaryRepository.save(salary);
        }
    }

    public void deleteSalary(long id) {
        Salary salary = salaryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Salary not found"));
        salaryRepository.delete(salary);
    }
}
