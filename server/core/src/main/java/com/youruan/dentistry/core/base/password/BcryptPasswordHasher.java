package com.youruan.dentistry.core.base.password;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class BcryptPasswordHasher implements PasswordHasher {

    @Override
    public String hash(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    @Override
    public boolean match(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

}
