package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.FinancialGoalDTO;
import com.mateeusferro.backend.dtos.InvestmentsDTO;
import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.services.FinancialGoalService;
import com.mateeusferro.backend.services.InvestmentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/investments")
public class InvestmentsController {
    @Autowired
    private InvestmentsService investmentsService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid InvestmentsDTO investmentsDTO){
        investmentsService.createInvestment(investmentsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Investment created",
                HttpStatus.CREATED));
    }
}
