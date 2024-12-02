package com.stagora.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stagora.dao.employers.DaoEmployeur;
import com.stagora.dao.students.DaoEtablissement;
import com.stagora.dao.students.DaoEtudiant;
import com.stagora.dao.users.DaoConnexion;
import com.stagora.dao.users.DaoUser;
import com.stagora.entities.employers.Employeur;
import com.stagora.entities.students.Etablissement;
import com.stagora.entities.students.Etudiant;
import com.stagora.entities.users.Connexion;
import com.stagora.entities.users.User;
import com.stagora.utils.exceptions.EmailNonDisponibleException;
import com.stagora.utils.user.RequestInscriptionEmployeur;
import com.stagora.utils.user.RequestInscriptionEtudiant;
import com.stagora.utils.user.TypeCompte;

@Service
public class ServiceUser {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private DaoEtudiant daoEtudiant;

    @Autowired
    private DaoEtablissement daoEtablissement;

    @Autowired
    private DaoEmployeur daoEmployeur;

    @Autowired
    private DaoConnexion daoConnexion;

    @Autowired
    private ServiceMailing serviceMailing;

    // Inscription étudiant
    public ResponseEntity<Map<String, String>> inscriptionEtudiant(RequestInscriptionEtudiant req) {
        User user = req.getUser();
        this.verifDisponibiliteEmail(user);

        Etudiant etudiant = req.getEtudiant();
        Optional<Etablissement> etablissement = daoEtablissement.findById(req.getId_etablissement());

        if (!etablissement.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.reponse("Établissement non trouvé"));
        }

        user.setTypeCompte(TypeCompte.ETUDIANT);
        daoUser.save(user);

        etudiant.setUser(user);
        etudiant.setEtablissement(etablissement.get());
        daoEtudiant.save(etudiant);

        envoyerEmailConfirmation(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.reponse("Inscription réussie. Veuillez vérifier votre email pour confirmer votre compte."));
    }

    // Inscription employeur
    public ResponseEntity<Map<String, String>> inscriptionEmployeur(RequestInscriptionEmployeur req) {
        User user = req.getUser();
        this.verifDisponibiliteEmail(user);

        Employeur employeur = req.getEmployeur();
        user.setTypeCompte(TypeCompte.EMPLOYEUR);
        daoUser.save(user);

        employeur.setUser(user);
        employeur.getSites().forEach(site -> site.setEmployeur(employeur));

        daoEmployeur.save(employeur);

        envoyerEmailConfirmation(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.reponse("Inscription réussie. Veuillez vérifier votre email pour confirmer votre compte."));
    }

    // Connexion utilisateur
    public ResponseEntity<Map<String, String>> connection(String email, String mdp) {
        User user = daoUser.findUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.reponse("Utilisateur non trouvé"));
        }

        if (this.verifMotdPasse(user, mdp)) {
            Connexion connexion = daoConnexion.findConnexionByUserId(user.getId());
            if (connexion != null) {
                connexion.setNombre_connexion(connexion.getNombre_connexion() + 1);
            } else {
                connexion = new Connexion();
            }
            connexion.setDate_connexion(LocalDateTime.now());
            connexion.setUser(user);
            daoConnexion.save(connexion);
            
            
            //    IL FAUT AMELIORER CE CODE 
            // Inclure l'ID utilisateur dans la réponse
            Map<String,String> response = new HashMap<>();
            response.put("message", "Connection réussie");
            response.put("userId", String.valueOf(user.getId())); // Ajouter l'ID utilisateur
            response.put("email", user.getEmail());
            response.put("typeCompte", user.getTypeCompte().toString());
            

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(this.reponse("Mauvais mot de passe"));
        }
    }

    // Génération du token pour réinitialisation de mot de passe
    public void generateResetPasswordToken(String email) {
        User user = daoUser.findUserByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Aucun utilisateur trouvé avec cet email.");
        }
        String token = generateTokenForUser(user);
        user.setResetToken(token);
        daoUser.save(user);

        String resetLink = "http://localhost:8082/user/reset-password?token=" + token;
        String contenu = "<p>Bonjour,</p>" +
                         "<p>Vous avez demandé une réinitialisation de mot de passe. Veuillez cliquer sur le lien ci-dessous :</p>" +
                         "<a href=\"" + resetLink + "\">Réinitialiser mon mot de passe</a>";

        serviceMailing.confirmationInscription(user.getEmail(), contenu);
    }

    // Réinitialisation du mot de passe
    public ResponseEntity<Map<String, String>> resetPassword(String token, String newPassword) {
        User user = daoUser.findUserByResetToken(token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.reponse("Token invalide"));
        }

        user.setMot_de_passe(newPassword); // Remplacer par un hachage sécurisé comme BCrypt
        user.setResetToken(null);
        daoUser.save(user);

        return ResponseEntity.ok(this.reponse("Mot de passe réinitialisé avec succès"));
    }

    // Confirmation du compte via token
    public ResponseEntity<Map<String, String>> confirmAccount(String token) {
        User user = daoUser.findUserByConfirmationToken(token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.reponse("Token invalide"));
        }

        user.setConfirme(true);
        user.setConfirmationToken(null);
        daoUser.save(user);

        return ResponseEntity.ok(this.reponse("Compte confirmé avec succès"));
    }

    // Vérification de la disponibilité de l'email
    private void verifDisponibiliteEmail(User user) {
        if (daoUser.findUserByEmail(user.getEmail()) != null) {
            throw new EmailNonDisponibleException("Email non disponible");
        }
    }

    // Vérification du mot de passe
    private boolean verifMotdPasse(User user, String mdp) {
        return user.getMot_de_passe().equals(mdp); // Remplacer par BCrypt si nécessaire
    }

    // Réponse standard
    private Map<String, String> reponse(String message) {
        Map<String, String> reponse = new HashMap<>();
        reponse.put("message", message);
        return reponse;
    }

    // Méthode pour envoyer un email de confirmation
    private void envoyerEmailConfirmation(User user) {
        String token = generateTokenForUser(user);
        user.setConfirmationToken(token);
        daoUser.save(user);

        String confirmationLink = "http://localhost:8082/user/confirm?token=" + token;

        String contenu = "<p>Bonjour "  +
                         "<p>Merci de vous être inscrit sur notre plateforme. Veuillez cliquer sur le lien suivant pour confirmer votre compte :</p>" +
                         "<a href=\"" + confirmationLink + "\">Confirmer mon compte</a>";

        serviceMailing.confirmationInscription(user.getEmail(), contenu);
    }

    // Génération d'un token unique
    private String generateTokenForUser(User user) {
        return java.util.UUID.randomUUID().toString();
    }
}
