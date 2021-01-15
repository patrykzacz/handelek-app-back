package com.engthesis.demo.validator;

public interface ErrorMessages {
    String EMPTY_PASSWORD_MESSAGE = "Password cannot be empty";
    String EMPTY_NAME_MESSAGE = "First name cannot be empty";
    String EMPTY_SURNAME_MESSAGE = "Last name cannot be empty";
    String EMPTY_EMAIL_MESSAGE = "Email cannot be empty";
    String EMPTY_PHONE_NUMBER_MESSAGE = "Phone number cannot be empty";

    String WRONG_NAME_MESSAGE = "Username is invalid";
    String WRONG_SURNAME_MESSAGE = "Username is invalid";
    String WRONG_PHONE_NUMBER_MESSAGE = "Phone number is invalid";
    String WRONG_EMAIL_MESSAGE = "Email is invalid";
    String WRONG_PASSWORD_MESSAGE = "Password must contain, Minimum eight characters, at least one uppercase letter, one lowercase letter and one number";

    String NOT_AVAILABLE_EMAIL_MESSAGE = "Email is already taken";

}

