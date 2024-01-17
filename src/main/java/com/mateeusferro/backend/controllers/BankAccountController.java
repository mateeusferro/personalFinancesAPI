package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.BankAccountDTO;
import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.models.BankAccount;
import com.mateeusferro.backend.services.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/bankAccount")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity createBank(@RequestBody @Valid BankAccountDTO bankAccountDTO){
        bankAccountService.createBankAccount(bankAccountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Bank account created",
                HttpStatus.CREATED));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable long id, @RequestBody @Valid BankAccountDTO bankAccountDTO) {
        bankAccountService.updateBankAccount(id, bankAccountDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Bank account updated",
                HttpStatus.OK));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity partialUpdateBankAccount(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        bankAccountService.partialUpdateBankAccount(id, updates);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Bank account partially updated",
                HttpStatus.OK));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBankAccount(@PathVariable long id) {
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Bank account deleted",
                HttpStatus.OK));
    }
}
