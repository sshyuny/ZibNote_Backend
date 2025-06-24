package com.sshyu.zibnotes.domain.email.port.in;

import com.sshyu.zibnotes.domain.email.model.Email;

public interface EmailUseCase {
    
    void sendEmail(Email email);

}
