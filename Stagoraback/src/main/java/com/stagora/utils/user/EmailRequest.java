package com.stagora.utils.user;

public class EmailRequest {
    private String email;
    private String subject;
    private String contenu;

    // Constructeur par défaut
    public EmailRequest() {
    }

    // Constructeur avec paramètres
    public EmailRequest(String email, String subject, String contenu) {
        this.email = email;
        this.subject = subject;
        this.contenu = contenu;
    }

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}

