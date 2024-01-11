package com.mateeusferro.backend.dtos;

import org.springframework.http.HttpStatusCode;

public record LoginResponseDTO(String message, String token, HttpStatusCode status) {
}
