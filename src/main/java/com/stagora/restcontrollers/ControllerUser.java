package com.stagora.restcontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stagora.services.ServiceUser;
import com.stagora.utils.user.RequestInscriptionEmployeur;
import com.stagora.utils.user.RequestInscriptionEtudiant;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class ControllerUser {

    @Autowired
    private ServiceUser serviceUser;

    // Inscription étudiant
    @PostMapping("/inscription/etudiant")
    public ResponseEntity<Map<String, String>> addEtudiant(@RequestBody RequestInscriptionEtudiant req) {
        return serviceUser.inscriptionEtudiant(req);
    }

    // Inscription employeur
    @PostMapping("/inscription/employeur")
    public ResponseEntity<Map<String, String>> addEmployeur(@RequestBody RequestInscriptionEmployeur req) {
        return serviceUser.inscriptionEmployeur(req);
    }

    // Connexion utilisateur
    @PostMapping("/connexion")
    public ResponseEntity<Map<String, String>> connect(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String mdp = request.get("mdp");

        return serviceUser.connection(email, mdp);
    }

    // Mot de passe oublié
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email est requis"));
        }

        try {
            serviceUser.generateResetPasswordToken(email);
            return ResponseEntity.ok(Map.of("message", "Email de réinitialisation envoyé avec succès"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of("message", "Utilisateur introuvable avec cet email."));
        }
    }

    // Confirmation du compte via token
    @GetMapping("/confirm")
    public ResponseEntity<Map<String, String>> confirmAccount(@RequestParam("token") String token) {
        return serviceUser.confirmAccount(token);
    }

    // Réinitialisation du mot de passe via token
    @GetMapping("/reset-password")
    public ResponseEntity<Void> redirectToFrontend(@RequestParam("token") String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Redirection vers l'application Angular avec le token
        return ResponseEntity.status(302)
                .header("Location", "http://localhost:4200/reset-password?token=" + token)
                .build();
    } 
@PostMapping("/reset-password")
public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
    String token = request.get("token");
    String newPassword = request.get("newPassword");

    if (token == null || token.isEmpty() || newPassword == null || newPassword.isEmpty()) {
        return ResponseEntity.badRequest().body(Map.of("message", "Token et nouveau mot de passe sont requis"));
    }

    return serviceUser.resetPassword(token, newPassword);
}}
