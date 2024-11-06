package com.stagora.handlerexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.stagora.utils.exceptions.EmailNonDisponibleException;

@ControllerAdvice
public class UserExceptionHandler {
	
	// ERREUR REPONSE QUAND EMAIL EXISTANT
    @ExceptionHandler(EmailNonDisponibleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleEmailNonDisponibleException(EmailNonDisponibleException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
