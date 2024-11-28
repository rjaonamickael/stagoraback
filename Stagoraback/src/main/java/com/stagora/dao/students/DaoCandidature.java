package com.stagora.dao.students;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stagora.entities.students.Candidature;


public interface DaoCandidature extends JpaRepository<Candidature, Long>{

}
