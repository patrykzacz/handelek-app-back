package com.engthesis.demo.exception;

import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException {

    public String message;
    public UserNotFoundException() {this.message="User Not Found"; }
    public UserNotFoundException(String message) { this.message = message; }
}
