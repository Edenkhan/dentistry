package com.youruan.dentistry.core.base.password;

public interface PasswordHasher {

    String hash(String plaintext);

    boolean match(String plaintext, String hashed);

}
