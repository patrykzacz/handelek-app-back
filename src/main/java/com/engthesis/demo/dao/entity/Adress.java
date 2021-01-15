package com.engthesis.demo.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Adresses")
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String line_1;
    private String line_2;
    private String line_3;
    private String city;
    private String zipCode;
    private String lat;
    private String lang;
    @OneToOne(cascade = {CascadeType.ALL})
    private User user;



}
