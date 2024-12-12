package com.stagora.dao.students;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stagora.entities.students.Etudiant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DaoEtudiant extends JpaRepository<Etudiant, Long> {
    // Méthode pour trouver un étudiant via son userId
    @Query("SELECT e FROM Etudiant e WHERE e.user.id = :userId")
    Etudiant findByUserId(@Param("userId") Long userId);
}
