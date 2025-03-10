package com.postdm.backend.domain.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member { // 사용자 엔티티
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nickname; // 이름

    private String username; // 유저 아이디

    private String password; // 비밀번호

    private String email; // 이메일

    private String phone; // 전화번호

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public String makeBlur(String username) {
        String[] parts = username.split("");
        for(int i = 3; i < parts.length - 1; i++) {
            parts[i] = "*";
        }
        return String.join("", parts);
    }

}
