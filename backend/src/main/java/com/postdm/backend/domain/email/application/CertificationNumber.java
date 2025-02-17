package com.postdm.backend.domain.email.application;

public class CertificationNumber { // 인증 번호 생성기

    public static String getCertificationNumber() {
        String certificationNumber = "";

        for (int count = 0; count < 4; count++) { // 0 ~ 9까지의 무작위 수 4자리 숫자 반환
            certificationNumber += (int) (Math.random() * 10);
        }

        return certificationNumber;
    }
}
