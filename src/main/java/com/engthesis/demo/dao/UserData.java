package com.engthesis.demo.dao;

public class UserData {
    private String email;
    private String name;
    private String surname;
    private String number;
    private String role;
    public UserData() {
    }

    public UserData(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public UserData(String email, String name, String surname, String number) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String phone) {
        this.number = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
