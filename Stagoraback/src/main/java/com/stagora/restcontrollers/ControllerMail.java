package com.stagora.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stagora.services.ServiceMailing;

@RestController
@RequestMapping("/mail")
public class ControllerMail {

    @Autowired
    private ServiceMailing serviceMailing;

    @PostMapping("/send")
    public String sendCustomMail(@RequestParam String email, @RequestParam String subject, @RequestParam String contenu) {
        serviceMailing.sendCustomEmail(email, subject, contenu);
        return "Email personnalisé envoyé avec succès.";
    }
}
