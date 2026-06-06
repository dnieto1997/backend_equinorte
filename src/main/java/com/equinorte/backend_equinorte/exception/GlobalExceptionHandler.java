package com.equinorte.backend_equinorte.exception;

import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<?> handleJsonError(HttpMessageNotReadableException e) {

                String message = e.getMessage();

                if (message != null && message.contains("TipoUsuario")) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(Map.of(
                                                        "status", 400,
                                                        "error", "Bad Request",
                                                        "mensaje",
                                                        "Tipo de usuario inválido. Valores permitidos: OPERADOR o SUPERVISOR"));
                }

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "status", 400,
                                                "error", "Bad Request",
                                                "mensaje", "Datos inválidos (revisar JSON o estructura)"));
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<?> handleBadRequest(BadRequestException e) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "status", 400,
                                                "error", "Bad Request",
                                                "mensaje", e.getMessage()));
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<?> handleNotFound(ResourceNotFoundException e) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(Map.of(
                                                "status", 404,
                                                "error", "Not Found",
                                                "mensaje", e.getMessage()));
        }

        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {

                return ResponseEntity.status(e.getStatusCode())
                                .body(Map.of(
                                                "status", e.getStatusCode().value(),
                                                "error", e.getStatusCode().toString(),
                                                "mensaje", e.getReason()));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<?> handleEnumError(IllegalArgumentException e) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "status", HttpStatus.BAD_REQUEST.value(),
                                                "error", "Bad Request",
                                                "mensaje", "Solo se permite OPERADOR o SUPERVISOR"));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<?> handleGeneralError(Exception e) {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(Map.of(
                                                "status", 500,
                                                "error", "Internal Server Error",
                                                "mensaje", e.getMessage()));
        }
}