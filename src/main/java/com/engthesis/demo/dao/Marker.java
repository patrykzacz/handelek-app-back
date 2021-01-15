package com.engthesis.demo.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class Marker {
    private String lang;
    private String lat;
    private String email;
    private String name;
    private String surname;
    private String number;
    private Long id;

    public Marker() {
    }

    public Marker(String lang, String lat,Long id, String email, String name, String surname, String number) {
        this.lang = lang;
        this.lat = lat;
        this.id=id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.number = number;
    }

}
