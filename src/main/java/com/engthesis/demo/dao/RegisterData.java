package com.engthesis.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class RegisterData {

    private String email;
    private String name;
    private String surname;
    private String password;
    private String phone;



}
