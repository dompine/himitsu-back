package com.dompine.himitsu.dao;

import com.dompine.himitsu.entity.HasRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface HasReadDao extends JpaRepository<HasRead, Long>, JpaSpecificationExecutor<HasRead> {
    Optional<HasRead> findByUserIdAndPostId(long userId, long postId);
}
