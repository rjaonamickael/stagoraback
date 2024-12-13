package com.stagora.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stagora.services.ServiceMailing;
import com.stagora.utils.user.EmailRequest;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "http://localhost:4200") 
public class ControllerMail {

    @Autowired
    private ServiceMailing serviceMailing;
    

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendCustomMail(@RequestBody EmailRequest emailRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            serviceMailing.sendCustomEmail(emailRequest.getEmail(), emailRequest.getSubject(), emailRequest.getContenu());
            response.put("ok", true);
            response.put("message", "Email personnalisé envoyé avec succès.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("ok", false);
            response.put("message", "Erreur lors de l'envoi de l'email.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    
}
