package com.patrick.loginReg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.patrick.loginReg.models.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>  {
    User findByEmail(String email);
}
