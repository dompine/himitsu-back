package com.dompine.himitsu.dao;

import com.dompine.himitsu.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccessTokenDao  extends JpaRepository<AccessToken, Long>, JpaSpecificationExecutor<AccessToken> {
}
