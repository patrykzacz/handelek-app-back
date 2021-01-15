package com.engthesis.demo.dao;

import com.engthesis.demo.dao.entity.Adress;
import com.engthesis.demo.dao.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class AdressData {

    private String line_1;
    private String line_2;
    private String line_3;
    private String city;
    private String zipCode;
    private String lat;
    private String lang;
    public AdressData(){}

    public AdressData(String line_1, String line_2, String line_3, String city, String zipCode) {
        this.line_1 = line_1;
        this.line_2 = line_2;
        this.line_3 = line_3;
        this.city = city;
        this.zipCode = zipCode;
    }
}
