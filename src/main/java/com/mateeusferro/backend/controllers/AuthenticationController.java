package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.AuthenticationDTO;
import com.mateeusferro.backend.dtos.LoginResponseDTO;
import com.mateeusferro.backend.dtos.ResponseDTO;
import com.mateeusferro.backend.exceptions.TokenNullException;
import com.mateeusferro.backend.infra.security.TokenService;
import com.mateeusferro.backend.models.Users;
import com.mateeusferro.backend.repositories.UsersRepository;
import com.mateeusferro.backend.utils.Blacklist;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Blacklist blacklist;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.email(),
                authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Users) auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).
                body(new LoginResponseDTO("You're logged",token, HttpStatus.OK));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String authorizationHeader,
                                 HttpServletResponse response) {
        String token = extractTokenFromAuthorizationHeader(authorizationHeader);

        blacklist.addToBlacklist(token);

        response.setHeader("Authorization", "");

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("You're logged out!",
                HttpStatus.OK));
    }

    private String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if(authorizationHeader == null) throw new TokenNullException("You're without a token");
        return authorizationHeader.replace("Bearer ", "");
    }

}
