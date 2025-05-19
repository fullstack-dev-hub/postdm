package com.postdm.backend.domain.member.domain.repository;

import com.postdm.backend.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Member> findByUsername(String username);
    Member findByEmail(String email);
}
