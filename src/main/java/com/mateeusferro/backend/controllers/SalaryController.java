package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.InvestmentsDTO;
import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.dtos.SalaryDTO;
import com.mateeusferro.backend.services.SalaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/create")
    public ResponseEntity createSalary(@RequestBody @Valid SalaryDTO salaryDTO){
        salaryService.createSalary(salaryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Salary created",
                HttpStatus.CREATED));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateSalary(@PathVariable long id, @RequestBody @Valid SalaryDTO salaryDTO) {
        salaryService.updateSalary(id, salaryDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO("Salary updated",
                HttpStatus.NO_CONTENT));
    }
}
