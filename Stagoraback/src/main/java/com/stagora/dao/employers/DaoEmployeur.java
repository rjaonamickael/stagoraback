package com.stagora.dao.employers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stagora.entities.employers.Employeur;

public interface DaoEmployeur extends JpaRepository<Employeur, Long>{

}
