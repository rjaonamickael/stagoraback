package com.stagora.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stagora.services.ServiceMailing;
import com.stagora.utils.user.EmailRequest;

@RestController
@RequestMapping("/mail")
public class ControllerMail {

    @Autowired
    private ServiceMailing serviceMailing;
    

    @PostMapping("/send")
    public String sendCustomMail(@RequestBody EmailRequest emailRequest) {
    	try {
    		serviceMailing.sendCustomEmail(emailRequest.getEmail(), emailRequest.getSubject(), emailRequest.getContenu());
            return "Email personnalisé envoyé avec succès.";
        } catch (Exception e) {
            return "Erreur lors de l'envoi de l'email.";
        }
    }
    
}
