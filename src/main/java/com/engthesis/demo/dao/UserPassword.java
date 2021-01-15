package com.engthesis.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class UserPassword {

    private String oldPassword;
    private String newPassword;

}
