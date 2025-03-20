package com.postdm.backend.domain.email.application;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailProvider { // 이메일 발송을 위한 Provider

    private final JavaMailSender mailSender;

    private final String SUBJECT = "[포스트 디엠] 인증 메일 입니다.";

    public boolean sendCertificationMail(String email, String certificationNumber) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String htmlContent = getCertificationMessage(certificationNumber);

            helper.setTo(email);
            helper.setSubject(SUBJECT);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getCertificationMessage(String certificationNumber) {

        String message = "";
        message += "<h1 style='text-align: center;'>[포스트 디엠] 인증 메일</h1>";
        message += "<h3 style='text-align: center;'> 인증코드 : <strong style = 'font-size: 32px; letter-spacing: 8px;'>" + certificationNumber + "</strong></h3>";

        return message;
    }
}
