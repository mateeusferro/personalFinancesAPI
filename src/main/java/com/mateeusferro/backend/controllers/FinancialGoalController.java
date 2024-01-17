package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.FinancialGoalDTO;
import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.services.FinancialGoalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Financial goal updated",
                HttpStatus.OK));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity partialUpdateFinancialGoal(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        financialGoalService.partialUpdateFinancialGoal(id, updates);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Financial goal partially updated",
                HttpStatus.OK));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteFinancialGoal(@PathVariable long id) {
        financialGoalService.deleteFinancialGoal(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Financial goal deleted",
                HttpStatus.OK));
    }
}
