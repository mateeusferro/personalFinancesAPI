package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.BankAccountDTO;
import com.mateeusferro.backend.dtos.ExpensesDTO;
import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.services.ExpensesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/expenses")
public class ExpensesController {

    @Autowired
    ExpensesService expensesService;

    @PostMapping("/create")
    public ResponseEntity createExpense(@RequestBody @Valid ExpensesDTO expensesDTO){
        expensesService.createExpense(expensesDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Expense created",
                HttpStatus.CREATED));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateExpense(@PathVariable long id, @RequestBody @Valid ExpensesDTO expensesDTO) {
        expensesService.updateExpense(id, expensesDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO("Expense updated",
                HttpStatus.NO_CONTENT));
    }
}
