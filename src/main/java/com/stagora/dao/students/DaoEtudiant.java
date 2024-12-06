package com.stagora.dao.students;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stagora.entities.students.Etudiant;


public interface DaoEtudiant extends JpaRepository<Etudiant, Long>{

}
