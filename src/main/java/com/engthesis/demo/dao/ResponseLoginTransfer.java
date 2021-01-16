package com.engthesis.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseLoginTransfer {

    private String message;
    private TokenData tokenData;

}
