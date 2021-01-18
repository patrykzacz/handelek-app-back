package com.engthesis.demo.repository;

import com.engthesis.demo.dao.GroupData;
import com.engthesis.demo.dao.UserData;
import com.engthesis.demo.dao.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {



}
