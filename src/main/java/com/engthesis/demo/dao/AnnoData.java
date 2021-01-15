package com.engthesis.demo.dao;

import com.engthesis.demo.dao.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class AnnoData {

    private Long id;
    private String text;
    private Date date;
    private String email;
    private String name;
    private String surname;
    private Long uid;

    public AnnoData() {
    }

    public AnnoData(Long id,String text, Date date, String email, String name, String surname) {
        this.id=id;
        this.text = text;
        this.date = date;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public AnnoData(Long id,String text, Date date,Long uid, String email, String name, String surname) {
        this.id=id;
        this.text = text;
        this.date = date;
        this.uid=uid;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

}
