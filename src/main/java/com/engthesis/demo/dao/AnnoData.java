package com.engthesis.demo.dao;

import com.engthesis.demo.dao.entity.User;

import java.util.Date;

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

    public AnnoData(String text) {
        this.text = text;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) {
        this.text = text;
    }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
