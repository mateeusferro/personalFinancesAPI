package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.FinancialGoalDTO;
import com.mateeusferro.backend.dtos.InvestmentsDTO;
import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.dtos.ResponseObjectDTO;
import com.mateeusferro.backend.models.Investments;
import com.mateeusferro.backend.services.FinancialGoalService;
import com.mateeusferro.backend.services.InvestmentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/investments")
public class InvestmentsController {
    @Autowired
    private InvestmentsService investmentsService;

    @GetMapping("/{userId}")
    public ResponseEntity getInvestments(@PathVariable long userId){
        List<Investments> investments = investmentsService.getInvestments(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObjectDTO("Found investment for this user",
                investments, HttpStatus.OK));
    }

    @PostMapping("/create")
    public ResponseEntity createInvestment(@RequestBody @Valid InvestmentsDTO investmentsDTO){
        investmentsService.createInvestment(investmentsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Investment created",
                HttpStatus.CREATED));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateInvestment(@PathVariable long id, @RequestBody @Valid InvestmentsDTO investmentsDTO) {
        investmentsService.updateInvestment(id, investmentsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Investment updated",
                HttpStatus.OK));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity partialUpdateInvestments(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        investmentsService.partialUpdateInvestments(id, updates);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Investment partially updated",
                HttpStatus.OK));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteInvestments(@PathVariable long id) {
        investmentsService.deleteInvestments(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Investment deleted",
                HttpStatus.OK));
    }
}
