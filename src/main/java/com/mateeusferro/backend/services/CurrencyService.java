package com.mateeusferro.backend.services;

import com.mateeusferro.backend.models.Currency;
import com.mateeusferro.backend.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    public List<Currency> getCurrency(){
        return currencyRepository.findAll();
    }
}
