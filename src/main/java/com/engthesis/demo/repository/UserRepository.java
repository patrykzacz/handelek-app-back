package com.engthesis.demo.repository;

import com.engthesis.demo.dao.LoginData;
import com.engthesis.demo.dao.UserData;
import com.engthesis.demo.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT new com.engthesis.demo.dao.UserData(u.email, u.role) " +
            "FROM  User u WHERE u.email = ?1")
    Optional<UserData> findTokenUserData(String email);

    @Query(value = "SELECT new com.engthesis.demo.dao.UserData(u.email, u.name, u.surname, u.number) " +
            "FROM  User u WHERE u.email = ?1")
    Optional<UserData> findUserData(String email);

    @Query(value = "SELECT new com.engthesis.demo.dao.LoginData(u.email, u.password,u.role) " +
            "FROM  User u WHERE u.email = ?1")
    Optional<LoginData> findUserPassword(String email);

    @Override
    void deleteById(Long aLong);

    Optional<User> findByNumber(String number);
}
