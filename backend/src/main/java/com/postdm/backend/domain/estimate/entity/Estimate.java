package com.postdm.backend.domain.estimate.entity;

import com.postdm.backend.domain.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "estimate")
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private LocalDateTime createdAt;

    @Builder
    public Estimate(String content, Member member) {
        this.title = generateTitle(content);
        this.content = content;
        this.member = member;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public boolean isOwner(Member member) {
        return this.member.equals(member);
    }

    public void update(String content) {
        this.title = generateTitle(content);
        this.content = content;
    }

    private String generateTitle(String content) {
        return content.length() > 10 ? content.substring(0, 10) + "..." : content;
    }
}