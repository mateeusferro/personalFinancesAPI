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

import java.util.Map;

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
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Expense updated",
                HttpStatus.OK));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity partialUpdateExpense(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        expensesService.partialUpdateExpense(id, updates);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Expense partially updated",
                HttpStatus.OK));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteExpense(@PathVariable long id) {
        expensesService.deleteExpense(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Expense deleted",
                HttpStatus.OK));
    }
}
