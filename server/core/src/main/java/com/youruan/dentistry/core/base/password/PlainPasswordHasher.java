package com.youruan.dentistry.core.base.password;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class PlainPasswordHasher implements PasswordHasher {

    @Override
    public String hash(String plaintext) {
        return plaintext;
    }

    @Override
    public boolean match(String plaintext, String hashed) {
        return plaintext.equals(hashed);
    }

}
