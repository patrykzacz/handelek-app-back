package com.engthesis.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Data
@AllArgsConstructor
public class WrongTokenException extends RuntimeException {
    private String message;
    public WrongTokenException() {this.message="Wrong Token";
    }
}
