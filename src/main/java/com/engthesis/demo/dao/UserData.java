package com.engthesis.demo.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
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

}
