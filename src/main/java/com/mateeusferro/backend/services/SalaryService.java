package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.InvestmentsDTO;
import com.mateeusferro.backend.dtos.SalaryDTO;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
import com.mateeusferro.backend.models.Investments;
import com.mateeusferro.backend.models.Salary;
import com.mateeusferro.backend.repositories.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

    @Autowired
    SalaryRepository salaryRepository;

    public void createSalary(SalaryDTO salaryDTO){
        Salary salary = new Salary(salaryDTO.type(), salaryDTO.value(), salaryDTO.date(),
                                    salaryDTO.description(), salaryDTO.usersId(), salaryDTO.currencyId());
        salaryRepository.save(salary);
    }

    public void updateSalary(long id, SalaryDTO salaryDTO){
        Salary updateSalary = salaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salary not exist with id: " + id));

        updateSalary.setType(salaryDTO.type());
        updateSalary.setValue(salaryDTO.value());
        updateSalary.setDate(salaryDTO.date());
        updateSalary.setDescription(salaryDTO.description());
        updateSalary.setUsersId(salaryDTO.usersId());
        updateSalary.setCurrencyId(salaryDTO.currencyId());

        salaryRepository.save(updateSalary);
    }
}
