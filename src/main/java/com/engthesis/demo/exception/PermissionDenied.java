package com.engthesis.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissionDenied extends RuntimeException{

    private String message;

    public PermissionDenied() { this.message="Permission Denied";
    }
}
