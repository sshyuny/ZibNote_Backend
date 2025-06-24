package com.sshyu.zibnote.domain.email.port.in;

import com.sshyu.zibnote.domain.email.model.Email;

public interface EmailUseCase {
    
    void sendEmail(Email email);

}
