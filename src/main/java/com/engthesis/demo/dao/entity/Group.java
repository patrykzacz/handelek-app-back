package com.engthesis.demo.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static com.engthesis.demo.validator.ErrorMessages.EMPTY_NAME_MESSAGE;

@Entity
@Data
@NoArgsConstructor
@Table(name = "userGroups")
public class Group{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Field Cannot be Empty")
    private Long creatorId;
    @NotNull(message = "Field Cannot be Empty")
    private String name;
    @NotNull(message = "Field Cannot be Empty")
    private String description;


    @ManyToMany(mappedBy = "userGroups")
    @EqualsAndHashCode.Exclude @ToString.Exclude
    Set<User> members;
}
