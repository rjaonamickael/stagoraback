package com.stagora.handlerexceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.stagora.utils.exceptions.EmailNonDisponibleException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	// ERREUR REPONSE QUAND EMAIL EXISTANT
    @ExceptionHandler(EmailNonDisponibleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)			// CONFLICT création de ressource avec des données déjà existantes.
    public ResponseEntity<Map<String, String>> handleEmailNonDisponibleException(EmailNonDisponibleException ex) {
    	
    	Map<String, String> reponse = new HashMap<>();
		reponse.put("error", ex.getMessage());
		
		
        return ResponseEntity.status(HttpStatus.CONFLICT).body(reponse);
    }
    
    // Erreur quand findById ne retourne aucun objet
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)			// NOT FOUND Ressource demandée introuvable.
    public ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException ex) {
    	
    	Map<String, String> reponse = new HashMap<>();
		reponse.put("error", ex.getMessage());
		
		
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reponse);
    }
}
