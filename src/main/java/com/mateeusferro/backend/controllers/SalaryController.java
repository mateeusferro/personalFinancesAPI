package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.InvestmentsDTO;
import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.dtos.ResponseObjectDTO;
import com.mateeusferro.backend.dtos.SalaryDTO;
import com.mateeusferro.backend.models.Salary;
import com.mateeusferro.backend.services.SalaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping("/{userId}")
    public ResponseEntity getSalary(@PathVariable long userId){
        List<Salary> salary = salaryService.getSalary(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObjectDTO("Found salary for this user",
                salary, HttpStatus.OK));
    }

    @PostMapping("/create")
    public ResponseEntity createSalary(@RequestBody @Valid SalaryDTO salaryDTO){
        salaryService.createSalary(salaryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Salary created",
                HttpStatus.CREATED));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateSalary(@PathVariable long id, @RequestBody @Valid SalaryDTO salaryDTO) {
        salaryService.updateSalary(id, salaryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Salary updated",
                HttpStatus.OK));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity partialUpdateSalary(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        salaryService.partialUpdateSalary(id, updates);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Salary partially updated",
                HttpStatus.OK));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteSalary(@PathVariable long id) {
        salaryService.deleteSalary(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Salary deleted",
                HttpStatus.OK));
    }
}
