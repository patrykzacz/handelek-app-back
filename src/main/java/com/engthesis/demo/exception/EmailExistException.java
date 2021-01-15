package com.engthesis.demo.exception;

import lombok.Data;

@Data
public class EmailExistException extends RuntimeException {
    private String message;
    public EmailExistException() { this.message="Email dont exist in base"; }
    public EmailExistException(String message1) { this.message = message1; }
}
