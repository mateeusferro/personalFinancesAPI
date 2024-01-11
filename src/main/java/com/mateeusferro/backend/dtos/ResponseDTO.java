package com.mateeusferro.backend.dtos;

import org.springframework.http.HttpStatusCode;

public record ResponseDTO(String message, HttpStatusCode status) {
}
