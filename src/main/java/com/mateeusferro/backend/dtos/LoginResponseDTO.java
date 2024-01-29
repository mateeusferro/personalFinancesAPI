package com.mateeusferro.backend.dtos;

import org.springframework.http.HttpStatusCode;

public record LoginResponseDTO(String message, Long id , String token, String refreshToken, HttpStatusCode status) {
}
