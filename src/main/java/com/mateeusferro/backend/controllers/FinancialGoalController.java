package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.ExpensesDTO;
import com.mateeusferro.backend.dtos.FinancialGoalDTO;
import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.services.FinancialGoalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/financialGoal")
public class FinancialGoalController {

    @Autowired
    private FinancialGoalService financialGoalService;

    @PostMapping("/create")
    public ResponseEntity createFinancialGoal(@RequestBody @Valid FinancialGoalDTO financialGoalDTO){
        financialGoalService.createFinancialGoal(financialGoalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Financial goal created",
                HttpStatus.CREATED));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateFinancialGoal(@PathVariable long id, @RequestBody @Valid FinancialGoalDTO
            financialGoalDTO) {
        financialGoalService.updateFinancialGoal(id, financialGoalDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO("Financial goal updated",
                HttpStatus.NO_CONTENT));
    }
}
