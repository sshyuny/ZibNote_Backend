package com.sshyu.zibnotes.application.service.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sshyu.zibnotes.domain.email.model.Email;
import com.sshyu.zibnotes.application.service.email.EmailService;

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
