package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.dtos.UsersDTO;
import com.mateeusferro.backend.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody @Valid UsersDTO usersDTO){
        usersService.createUser(usersDTO);
        ResponseDTO responseDTO = new ResponseDTO("User created", HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable long id, @RequestBody @Valid UsersDTO usersDTO) {
        usersService.updateUser(id, usersDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("User updated",
                HttpStatus.OK));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity partialUpdateUsers(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        usersService.partialUpdateUsers(id, updates);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("User partially updated",
                HttpStatus.OK));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUsers(@PathVariable long id) {
        usersService.deleteUsers(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("User deleted",
                HttpStatus.OK));
    }
}
