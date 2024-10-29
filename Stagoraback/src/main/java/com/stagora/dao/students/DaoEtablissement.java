package com.stagora.dao.students;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stagora.entities.students.Etablissement;

public interface DaoEtablissement extends JpaRepository<Etablissement, Long>{

}
