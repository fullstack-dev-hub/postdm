package com.postdm.backend.domain.estimate.entity;

import com.postdm.backend.domain.member.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    Page<Estimate> findAll(Pageable pageable); // 관리자용
    Page<Estimate> findByMember(Member member, Pageable pageable); // 사용자용
}