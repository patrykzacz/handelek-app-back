package com.engthesis.demo.exception;

import lombok.Data;

@Data
public class InvalidInputException extends RuntimeException {
    public String message;


    public InvalidInputException() {
        this.message="Invalid Input Excpetion";
    }
    public InvalidInputException(String message) {
        this.message=message;
    }
}
