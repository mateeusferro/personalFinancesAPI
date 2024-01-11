package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.dtos.SalaryDTO;
import com.mateeusferro.backend.services.SalaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid SalaryDTO salaryDTO){
        salaryService.createSalary(salaryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Salary created",
                HttpStatus.CREATED));
    }
}
