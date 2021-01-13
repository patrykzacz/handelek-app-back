package com.engthesis.demo.repository;

import com.engthesis.demo.dao.AnnoData;
import com.engthesis.demo.dao.entity.Announcement;
import com.engthesis.demo.dao.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnoucementRepository extends JpaRepository<Announcement, Long> {

    @Query(value = "SELECT new com.engthesis.demo.dao.AnnoData(a.id,a.text,a.date, a.user.email, a.user.name,a.user.surname) " +
            "FROM  Announcement a inner Join a.user u on u.id=?1")
    List<AnnoData> findUserAnno(Long id);

    @Override
    Optional<Announcement> findById(Long aLong);

    List<Announcement> findAll();

    @Override
    void deleteById(Long aLong);

    @Query(value = "SELECT new com.engthesis.demo.dao.AnnoData(a.id,a.text,a.date, a.user.email, a.user.name,a.user.surname) " +
            "FROM  Announcement a inner Join a.user u on u.id=?1")
    List<AnnoData>findByUserId(Long id);

    @Query(value = "SELECT new com.engthesis.demo.dao.AnnoData(a.id,a.text,a.date,a.user.id, a.user.email, a.user.name,a.user.surname) " +
            "FROM  Announcement a")
    List<AnnoData> getAll();
}
