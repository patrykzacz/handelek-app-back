package com.engthesis.demo.dao.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Table(name = "Users", indexes = @Index(columnList = "email"))
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String password;
    @Email
    private String email;
    private String number;

    private String role;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Announcement> annoucments;


    public User(){}

    public User(String name, String surname, String password, @Email String email, String number) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.number = number;
        this.role="ROLE_USER";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surrname) {
        this.surname = surrname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passowrd) {
        this.password = passowrd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Set<Announcement> getAnnoucments() {
        return annoucments;
    }

    public void setAnnoucments(Set<Announcement> annoucments) {
        this.annoucments = annoucments;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
