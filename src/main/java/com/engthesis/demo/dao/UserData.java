package com.engthesis.demo.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.engthesis.demo.validator.ErrorMessages.*;
import static com.engthesis.demo.validator.ErrorMessages.WRONG_NAME_MESSAGE;
import static com.engthesis.demo.validator.ValidatorRegex.*;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    @NotNull(message = EMPTY_EMAIL_MESSAGE)
    @Pattern(regexp = EMAIL_VALIDATION_REGEX, message = WRONG_EMAIL_MESSAGE)
    private String email;
    @NotNull(message = EMPTY_NAME_MESSAGE)
    @Size(min= 2, max =30, message = WRONG_NAME_MESSAGE )
    @Pattern(regexp = NAME_VALIDATION_REGEXT, message = "Name is Invalid")
    private String name;
    @NotNull(message = EMPTY_NAME_MESSAGE)
    @Size(min= 2, max =30, message = WRONG_NAME_MESSAGE )
    @Pattern(regexp = NAME_VALIDATION_REGEXT, message = "Surname is Invalid")
    private String surname;
    @NotNull(message = EMPTY_PHONE_NUMBER_MESSAGE)
    @Pattern(regexp = PHONE_NUMBER_VALIDATION_REGEX, message = WRONG_PHONE_NUMBER_MESSAGE)
    private String number;
    private String role;

    public UserData(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public UserData(String email, String name, String surname, String number) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.number = number;
    }

}
