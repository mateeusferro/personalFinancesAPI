package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.FinancialGoalDTO;
import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.services.FinancialGoalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/financialGoal")
public class FinancialGoalController {

    @Autowired
    private FinancialGoalService financialGoalService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid FinancialGoalDTO financialGoalDTO){
        financialGoalService.createFinancialGoal(financialGoalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Financial goal created",
                HttpStatus.CREATED));
    }
}
