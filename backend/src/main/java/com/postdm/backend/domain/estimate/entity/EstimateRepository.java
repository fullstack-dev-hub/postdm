package com.postdm.backend.domain.estimate.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    List<Estimate> findByMemberId(Long memberId);
}