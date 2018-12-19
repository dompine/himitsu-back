package com.dompine.himitsu.dao;

import com.dompine.himitsu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUserOpenid(String openid);
}
