package com.stagora.dao.employers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stagora.entities.employers.Employeur;
import com.stagora.entities.users.User;

public interface DaoEmployeur extends JpaRepository<Employeur, Long>{
	// Méthode dans votre DAO pour rechercher un Employeur à partir d'un id_user
	Optional<Employeur> findByUserId(Long userId);

}

