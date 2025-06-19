package com.sshyu.zibnote.application.service.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnote.domain.email.model.Email;

import jakarta.transaction.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class EmailServiceTest {
    
    @Autowired
    EmailService emailService;

    @Test
    void test() {
        Email email = Email.builder()
            .from(null)
            .to("")
            .subject("test subject")
            .text("test text")
            .build();
    //     emailService.sendEmail(email);
    }

}
