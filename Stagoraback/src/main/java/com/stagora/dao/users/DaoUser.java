package com.stagora.dao.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stagora.entities.users.User;


public interface DaoUser extends JpaRepository<User, Long>{

}
