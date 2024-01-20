package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.ResponseObjectDTO;
import com.mateeusferro.backend.models.Currency;
import com.mateeusferro.backend.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping("")
    public ResponseEntity getCurrency(){
        List<Currency> currency = currencyService.getCurrency();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObjectDTO("Found currencies",
                currency, HttpStatus.OK));
    }
}
