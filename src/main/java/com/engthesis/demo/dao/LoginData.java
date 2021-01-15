package com.engthesis.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.engthesis.demo.validator.ErrorMessages.*;
import static com.engthesis.demo.validator.ErrorMessages.WRONG_PASSWORD_MESSAGE;
import static com.engthesis.demo.validator.ValidatorRegex.EMAIL_VALIDATION_REGEX;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class LoginData {

    @NotNull(message = EMPTY_EMAIL_MESSAGE)
    @Pattern(regexp = EMAIL_VALIDATION_REGEX, message = WRONG_EMAIL_MESSAGE)
    private String email;
    @NotNull(message = EMPTY_PASSWORD_MESSAGE)
    @Size(min= 8, max =30, message = WRONG_PASSWORD_MESSAGE )
    private String password;
    private String role;


}
