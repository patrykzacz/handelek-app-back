package com.engthesis.demo.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;
import static com.engthesis.demo.validator.ValidatorRegex.*;
import static com.engthesis.demo.validator.ErrorMessages.*;


@Data
@Entity
@AllArgsConstructor
@Table(name = "Users", indexes = @Index(columnList = "email"))
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = EMPTY_NAME_MESSAGE)
    @Size(min= 2, max =30, message = WRONG_NAME_MESSAGE )
    @Pattern(regexp = NAME_VALIDATION_REGEXT, message = "Name is Invalid")
    private String name;
    @NotNull(message = EMPTY_SURNAME_MESSAGE)
    @Size(min= 2, max =30, message = WRONG_SURNAME_MESSAGE )
    @Pattern(regexp = NAME_VALIDATION_REGEXT, message = WRONG_SURNAME_MESSAGE)
    private String surname;
    @NotNull(message = EMPTY_PASSWORD_MESSAGE)
    @Pattern(regexp = PASSWORD_VALIDATION_REGEX, message = WRONG_PASSWORD_MESSAGE)
    private String password;
    @Column(unique = true)
    @NotNull(message = EMPTY_EMAIL_MESSAGE)
    @Pattern(regexp = EMAIL_VALIDATION_REGEX, message = WRONG_EMAIL_MESSAGE)
    private String email;
    @NotNull(message = EMPTY_PHONE_NUMBER_MESSAGE)
    @Pattern(regexp = PHONE_NUMBER_VALIDATION_REGEX, message = WRONG_PHONE_NUMBER_MESSAGE)
    private String number;

    private String role;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Announcement> annoucments;

    public User(){this.role="ROLE_USER";}
    public User(String name, String surname, String password,String email, String number) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.number = number;
        this.role="ROLE_USER";
    }
}
