package com.engthesis.demo.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "Announcments")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column
    @Type(type="text")
    public String text;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date date;


}
