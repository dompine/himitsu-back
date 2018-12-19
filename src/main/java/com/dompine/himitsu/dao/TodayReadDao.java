package com.dompine.himitsu.dao;

import com.dompine.himitsu.entity.TodayRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface TodayReadDao extends JpaRepository<TodayRead, Long>, JpaSpecificationExecutor<TodayRead> {
    List<TodayRead> findAllByUserId(long userId);
}
