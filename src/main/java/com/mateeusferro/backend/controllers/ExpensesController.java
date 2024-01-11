package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.ExpensesDTO;
import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.services.ExpensesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/expenses")
public class ExpensesController {

    @Autowired
    ExpensesService expensesService;

    @PostMapping("/create")
    public ResponseEntity createExpense(@RequestBody @Valid ExpensesDTO expensesDTO){
        expensesService.createExpense(expensesDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Expense create",
                HttpStatus.CREATED));
    }
}
