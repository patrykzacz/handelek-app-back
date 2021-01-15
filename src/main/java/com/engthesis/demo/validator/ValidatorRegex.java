package com.engthesis.demo.validator;

public interface ValidatorRegex {

    // Minimum eight characters, at least one uppercase letter,
    // one lowercase letter, one number and one special character:
    String PASSWORD_VALIDATION_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    //Name and surname
    String NAME_VALIDATION_REGEXT="^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$";
    //Email Validation
    String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    //Polish Phone Number
    String PHONE_NUMBER_VALIDATION_REGEX = "(?<!\\w)(\\(?(\\+|00)?48\\)?)?[ -]?\\d{3}[ -]?\\d{3}[ -]?\\d{3}(?!\\w)";
}
