package com.postdm.backend.domain.auth.api;

import com.postdm.backend.global.template.ResponseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController { // 현재 사용자 세션 확인용 테스트 컨트롤러
    @GetMapping("/test")
    public ResponseTemplate<String> test() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return new ResponseTemplate<>(HttpStatus.OK, "환영합니다, " + username + "님!", username);
    }
}
