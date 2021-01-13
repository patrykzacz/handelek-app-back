package com.engthesis.demo.repository;

import com.engthesis.demo.dao.AdressData;
import com.engthesis.demo.dao.Marker;
import com.engthesis.demo.dao.entity.Adress;
import com.engthesis.demo.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdressRepository extends JpaRepository<Adress, Long> {

    @Override
    Optional<Adress> findById(Long aLong);

    @Query(value = "SELECT new com.engthesis.demo.dao.AdressData(a.line_1,a.line_2,a.line_3, a.city,a.zipCode) " +
            "FROM  Adress a where a.user.id= ?1")
    Optional<Adress> findByUserId(Long id);

    @Query(value = "SELECT new com.engthesis.demo.dao.Marker(a.lat, a.lang, u.id, u.email, u.name, u.surname, u.number ) " +
            "FROM  Adress a, User u where a.user.id=u.id")
    List<Marker> allMarkers();
}
