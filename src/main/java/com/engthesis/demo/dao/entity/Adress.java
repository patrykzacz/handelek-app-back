package com.engthesis.demo.dao.entity;

import javax.persistence.*;

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

    public Adress() {
    }


    public Adress(String line_1,
                  String line_2, String line_3,
                  String city, String zipCode, User user,
                  String lat, String lang) {
        this.line_1 = line_1;
        this.line_2 = line_2;
        this.line_3 = line_3;
        this.city = city;
        this.zipCode = zipCode;
        this.user=user;
        this.lang=lang;
        this.lat=lat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLine_1() {
        return line_1;
    }

    public void setLine_1(String line_1) {
        this.line_1 = line_1;
    }

    public String getLine_2() {
        return line_2;
    }

    public void setLine_2(String line_2) {
        this.line_2 = line_2;
    }

    public String getLine_3() {
        return line_3;
    }

    public void setLine_3(String line_3) {
        this.line_3 = line_3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
