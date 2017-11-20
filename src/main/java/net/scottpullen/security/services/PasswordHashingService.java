package net.scottpullen.security.services;

import com.lambdaworks.crypto.SCryptUtil;

public class PasswordHashingService {

    public static String perform(String password) {
        return SCryptUtil.scrypt(password, 16, 16, 16);
    }
}
