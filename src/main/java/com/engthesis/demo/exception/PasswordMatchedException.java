package com.engthesis.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordMatchedException extends RuntimeException {
    private String message;

    public PasswordMatchedException() {this.message="Password cannot be same"; }
}
