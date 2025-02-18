package com.postdm.backend.domain.member.domain.repository;

import com.postdm.backend.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Member findByUsername(String username);
}
