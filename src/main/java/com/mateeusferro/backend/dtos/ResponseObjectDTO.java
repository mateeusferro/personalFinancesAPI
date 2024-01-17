package com.mateeusferro.backend.dtos;

import org.springframework.http.HttpStatusCode;

public record ResponseObjectDTO<T>(String message, T data, HttpStatusCode status) {
}
