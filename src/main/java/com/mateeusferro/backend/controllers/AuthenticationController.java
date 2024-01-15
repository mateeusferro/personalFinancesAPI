package com.mateeusferro.backend.controllers;

import com.mateeusferro.backend.dtos.*;
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

        var token = tokenService.generateTokens((Users) auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).
                body(new LoginResponseDTO("You're logged", token.accessToken(),
                        token.refreshToken(), HttpStatus.OK));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity refreshToken(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO,
                                       @RequestHeader("Authorization") String authorizationHeader ){
        String token = extractTokenFromAuthorizationHeader(authorizationHeader);
        final String refreshToken;
        final String userEmail = tokenService.extractUsername(refreshTokenDTO.refreshToken());
        if (userEmail != null) {
            var user = this.usersRepository.findByEmail(userEmail);
            if (tokenService.isTokenValid(refreshTokenDTO.refreshToken(), user)) {
                var accessToken = tokenService.generateTokens((Users) user);
                blacklist.addToBlacklist(token);
                blacklist.addToBlacklist(refreshTokenDTO.refreshToken());
                return ResponseEntity.status(HttpStatus.OK).
                        body(new LoginResponseDTO("You're logged again", accessToken.accessToken(),
                                accessToken.refreshToken(), HttpStatus.OK));
            }
        }
        throw new RuntimeException("Something went wrong");
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
