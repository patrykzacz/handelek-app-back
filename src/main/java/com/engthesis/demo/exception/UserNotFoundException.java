package com.engthesis.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {

    public String message;
    public UserNotFoundException() {this.message="User Not Found"; }

}
