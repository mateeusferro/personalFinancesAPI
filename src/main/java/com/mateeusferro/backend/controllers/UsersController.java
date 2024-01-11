package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.dtos.UsersDTO;
import com.mateeusferro.backend.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody @Valid UsersDTO usersDTO){
        usersService.create(usersDTO);
        ResponseDTO responseDTO = new ResponseDTO("User created", HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
