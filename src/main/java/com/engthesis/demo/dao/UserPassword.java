package com.engthesis.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.engthesis.demo.validator.ErrorMessages.WRONG_PASSWORD_MESSAGE;
import static com.engthesis.demo.validator.ValidatorRegex.PASSWORD_VALIDATION_REGEX;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class UserPassword {

    private String oldPassword;
    @NotNull
    @Pattern(regexp = PASSWORD_VALIDATION_REGEX, message = WRONG_PASSWORD_MESSAGE)
    private String newPassword;

}
