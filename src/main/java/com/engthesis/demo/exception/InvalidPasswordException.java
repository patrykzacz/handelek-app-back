package com.engthesis.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidPasswordException extends RuntimeException {
    private String message;

    public InvalidPasswordException() {this.message="password mismatch";
    }
}
