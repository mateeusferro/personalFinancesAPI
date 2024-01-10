package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.SalaryDTO;
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
}
