package com.stagora.dao.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stagora.entities.users.User;

public interface DaoUser extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par email.
     * 
     * @param email L'email de l'utilisateur.
     * @return L'utilisateur correspondant à l'email ou null s'il n'existe pas.
     */
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(@Param("email") String email);

    /**
     * Recherche un utilisateur par son token de confirmation.
     * 
     * @param token Le token de confirmation.
     * @return L'utilisateur correspondant au token ou null s'il n'existe pas.
     */
    @Query("SELECT u FROM User u WHERE u.confirmationToken = :token")
    User findUserByConfirmationToken(@Param("token") String token);

    /**
     * Recherche un utilisateur par son token de réinitialisation de mot de passe.
     * 
     * @param token Le token de réinitialisation.
     * @return L'utilisateur correspondant au token ou null s'il n'existe pas.
     */
    @Query("SELECT u FROM User u WHERE u.resetToken = :token")
    User findUserByResetToken(@Param("token") String token);
}
