package com.engthesis.demo.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAlreadyBelongtoGroup extends RuntimeException{

    public String message;

    public UserAlreadyBelongtoGroup() { this.message="User Already Belong to Group";
    }
}
