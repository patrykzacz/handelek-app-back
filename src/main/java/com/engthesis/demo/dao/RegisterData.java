package com.engthesis.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class RegisterData {

    private String email;
    private String name;
    private String surname;
    private String password;
    private String phone;

    public RegisterData() {
    }


}
